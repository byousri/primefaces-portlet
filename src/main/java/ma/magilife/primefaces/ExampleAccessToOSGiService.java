package ma.magilife.primefaces;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.portal.kernel.service.UserLocalService;

/**
 * @author Yousri
 *
 */

@ManagedBean (name ="registeredUser")
@RequestScoped
public class ExampleAccessToOSGiService {
	
	private static final Logger _log = LoggerFactory.getLogger(ExampleAccessToOSGiService.class);
	private int countRegisteredUser;
	private UserLocalServiceTracker userLocalServiceTracker;

	@PostConstruct
	public void postConstruct() {
		try {
			Bundle bundle = FrameworkUtil.getBundle(this.getClass());
			BundleContext bundleContext = bundle.getBundleContext();
			
			userLocalServiceTracker = new UserLocalServiceTracker(bundleContext);
			userLocalServiceTracker.open();
			userLocalServiceTracker.waitForService(500);
			
			if (!userLocalServiceTracker.isEmpty()) {
				UserLocalService userLocalService = userLocalServiceTracker.getService();
				countRegisteredUser = userLocalService.getUsersCount();
				
				_log.info("Liferay total users: " + countRegisteredUser);
			}
			else {
				_log.error("User service is temporarily unavailable");
			}
		}
		catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}
	
	
	@PreDestroy
	public void preDestroy() {
		userLocalServiceTracker.close();
	}


	public int getCountRegisteredUser() {
		return countRegisteredUser;
	}

	public void setCountRegisteredUser(int countRegisteredUser) {
		this.countRegisteredUser = countRegisteredUser;
	}

	
}
