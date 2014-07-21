package ru.eastwind.menu;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MenuItem {
    public final String name;

    public MenuItem(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MenuItem other = (MenuItem) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "MenuItem [name=" + name + "]";
    }

    public static List<MenuItem> menuItems(String... names) {
        return Arrays.asList(names).stream().map(name -> new MenuItem(name)).collect(Collectors.toList());
    }
}