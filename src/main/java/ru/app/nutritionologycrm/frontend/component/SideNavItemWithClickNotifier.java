package ru.app.nutritionologycrm.frontend.component;

import com.vaadin.flow.component.ClickNotifier;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.sidenav.SideNavItem;

public class SideNavItemWithClickNotifier extends SideNavItem implements ClickNotifier<SideNavItem> {

    public SideNavItemWithClickNotifier(String label) {
        super(label);
    }

    public SideNavItemWithClickNotifier(String label, Component prefixComponent) {
        super(label);
        this.setPrefixComponent(prefixComponent);
    }

}
