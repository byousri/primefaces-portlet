package ma.magilife.primefaces;

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import com.liferay.portal.kernel.service.UserLocalService;

/**
 * @author Yousri
 *
 */
public class UserLocalServiceTracker extends ServiceTracker<UserLocalService, UserLocalService> {

	public UserLocalServiceTracker(BundleContext bundleContext) {
        super(bundleContext, UserLocalService.class, null);
    }
}
