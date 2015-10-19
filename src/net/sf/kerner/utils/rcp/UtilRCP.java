package net.sf.kerner.utils.rcp;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IBundleGroup;
import org.eclipse.core.runtime.IBundleGroupProvider;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.UIJob;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

public class UtilRCP {

	public static MultiStatus createMultiStatus(String id, Throwable t) {

		List<Status> childStatuses = new ArrayList<Status>();
		StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();

		for (StackTraceElement stackTrace : stackTraces) {
			Status status = new Status(IStatus.ERROR, "net.sf.geal.example.ui", stackTrace.toString());
			childStatuses.add(status);
		}

		MultiStatus ms;
		if (t != null)
			ms = new MultiStatus(id, IStatus.ERROR, childStatuses.toArray(new Status[] {}), t.toString(), t);
		else
			ms = new MultiStatus(id, IStatus.ERROR, childStatuses.toArray(new Status[] {}), "", null);
		return ms;
	}

	public static void enableMemoryMonitor() {
		final IPreferenceStore store = PlatformUI.getPreferenceStore();
		store.setValue("SHOW_MEMORY_MONITOR", true);
	}

	/**
	 * Call in UIJob
	 *
	 * @see UIJob
	 */
	public static String getCurrentViewId() {

		final IWorkbenchPage wbp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		return wbp.getActivePartReference().getId();

	}

	public static Display getDisplay() {
		Display display = Display.getCurrent();
		// may be null if outside the UI thread
		if (display == null) {
			display = Display.getDefault();
		}
		return display;
	}

	public static String getFeatureVersion(final String featureId) {
		String version = null;
		final IBundleGroupProvider[] providers = Platform.getBundleGroupProviders();
		if (providers != null) {
			for (final IBundleGroupProvider provider : providers) {
				final IBundleGroup[] bundleGroups = provider.getBundleGroups();
				for (final IBundleGroup group : bundleGroups) {
					if (group.getIdentifier().equals(featureId)) {
						version = group.getVersion();
					}
				}
			}
		}
		return version;
	}

	public static String getPlatformVersion() {
		String version = null;
		try {
			@SuppressWarnings("rawtypes")
			final Dictionary dictionary = org.eclipse.ui.internal.WorkbenchPlugin.getDefault().getBundle().getHeaders();
			version = (String) dictionary.get("Bundle-Version"); //$NON-NLS-1$
		} catch (final NoClassDefFoundError e) {
			version = getProductVersion();
		}
		return version;
	}

	static String getString(String pluginId, String key) {
		String value = "n/a";
		Bundle b = Platform.getBundle(pluginId);
		if (b != null) {
			final Dictionary dictionary = Platform.getBundle(pluginId).getHeaders();
			value = (String) dictionary.get(key); // $NON-NLS-1$
		}
		return value;
	}

	public static String getPluginName(final String pluginId) {
		return getString(pluginId, "Bundle-Name");
	}

	public static String getPluginVersion(final String pluginId) {
		return getString(pluginId, "Bundle-Version");
	}

	public static String getProductName() {
		return Platform.getProduct().getName();
	}

	public static String getProductVersion() {
		final IProduct product = Platform.getProduct();
		final Bundle bundle = product.getDefiningBundle();
		final Version v = bundle.getVersion();
		return v.toString();
	}

	public static List<Object> getSelection(final String viewID) {
		final IViewPart view = getViewByID(viewID);
		final ISelection selection = view.getSite().getSelectionProvider().getSelection();
		final List<Object> list = new ArrayList<Object>();
		if (selection != null && selection instanceof IStructuredSelection) {
			final IStructuredSelection sel = (IStructuredSelection) selection;
			for (final Iterator<?> iterator = sel.iterator(); iterator.hasNext();) {
				final Object obj = iterator.next();
				list.add(obj);
			}
		}
		return list;
	}

	/**
	 * Call in UI Thread.
	 *
	 */
	public static Shell getShell() {

		// PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
		// @Override
		// public void run() {
		// final Shell activeShell =
		// PlatformUI.getWorkbench().getActiveWorkbenchWindow()
		// .getShell();
		// }
		// });

		final IWorkbench wb = PlatformUI.getWorkbench();
		final IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		return win.getShell();
	}

	/**
	 * Call in UIJob
	 *
	 * @see UIJob
	 */
	public static IStatusLineManager getStatusLineForCurrentView() {
		return getViewByID(getCurrentViewId()).getViewSite().getActionBars().getStatusLineManager();
	}

	/**
	 * Returns {@code null} if view for given id is not visible at the moment.
	 * <br>
	 * Note: Call in UIJob
	 *
	 * @see UIJob
	 */
	public static IViewPart getViewByID(final String id) {
		final IWorkbench wb = PlatformUI.getWorkbench();
		final IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		final IWorkbenchPage page = win.getActivePage();
		return page.findView(id);
	}

	public static boolean isVisible(final IViewPart view, final IWorkbenchPartSite site) {
		final IWorkbenchPage page = site.getPage();
		return page.isPartVisible(view);
	}

}
