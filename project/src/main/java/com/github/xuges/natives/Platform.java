package com.github.xuges.natives;

public enum Platform {
    UNSUPPORTED("unsupported"),
    _AUTO(OS.CURRENT + "_" + Arch.CURRENT),
    ;

    public static final Platform CURRENT;
    static {
        Platform current = _AUTO;
        if (OS.CURRENT.ordinal() == OS.UNKNOWN.ordinal() || Arch.CURRENT.ordinal() == Arch.UNKNOWN.ordinal())
            current = UNSUPPORTED;
        CURRENT = current;
    }

    private final String value;

    Platform(String v) {
        value = v;
    }

    @Override
    public String toString() {
        return value;
    }
}
