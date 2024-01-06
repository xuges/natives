package com.github.xuges.natives;

public enum OS {
    UNKNOWN("unknown"),
    WINDOWS("windows"),
    MACOS("macos"),
    LINUX("linux"),
    ANDROID("android"),
    AIX("aix"),
    SOLARIS("solaris"),
    HPUX("hpux"),
    FREEBSD("freebsd"),
    OPENBSD("openbsd"),
    NETBSD("netbsd"),
    ;

    public static final OS CURRENT;
    static {
        OS current = UNKNOWN;
        String os = System.getProperty("os.name").toLowerCase().replaceAll("[^a-z0-9]", "");;

        if (os.contains("linux")) {
            current = LINUX;
            if (System.getProperty("java.vendor").toLowerCase().contains("android"))
                current = ANDROID;
        } else if (os.startsWith("windows")) {
            current = WINDOWS;
        } else if (os.startsWith("mac") || os.startsWith("osx")) {
            current = MACOS;
        } else if (os.startsWith("aix")) {
            current = AIX;
        } else if (os.startsWith("solaris") || os.startsWith("sunos")) {
            current = SOLARIS;
        } else if (os.startsWith("hpux")) {
            current = HPUX;
        } else if (os.startsWith("freebsd")) {
            current = FREEBSD;
        } else if (os.startsWith("openbsd")) {
            current = OPENBSD;
        } else if (os.startsWith("netbsd")) {
            current = NETBSD;
        }

        CURRENT = current;
    }

    private final String value;

    OS(String v) {
        value = v;
    }

    @Override
    public String toString() {
        return value;
    }

    public boolean isUnixLike() {
        return LINUX.ordinal() <= CURRENT.ordinal() && CURRENT.ordinal() <= NETBSD.ordinal();
    }
}
