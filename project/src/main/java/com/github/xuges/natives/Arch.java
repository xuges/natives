package com.github.xuges.natives;

public enum Arch {
    UNKNOWN("unknown"),
    X86("x86"),
    X64("x64"),
    ARMEL("armel"),
    ARMHF("armhf"),
    AARCH64("aarch64"),
    LOONGARCH64("loongarch64"),
    SW64("sw64"),
    RISCV("riscv"),
    RISCV64("riscv64"),
    MIPS("mips"),
    MIPSEL("mipsel"),
    MIPS64("mips64"),
    MIPSEL64("mipsel64"),
    PPC("ppc"),
    PPCLE("ppcle"),
    PPC64("ppc64"),
    PPC64LE("ppc64le"),
    SPARC("sparc"),
    SPARC64("sparc64"),
    S390("s390"),
    S390X("s390x"),
    ;

    public static final Arch CURRENT;
    static {
        Arch current = UNKNOWN;
        String arch = System.getProperty("os.arch").toLowerCase().replaceAll("[^a-z0-9]", "");

        if (arch.equals("x86") || arch.startsWith("i") && arch.endsWith("86"))
            current = X86;
        else if (arch.equals("amd64") || arch.equals("x86_64") || arch.equals("em64t"))
            current = X64;
        else if (arch.startsWith("armel") || arch.equals("armeabi") || arch.equals("armv5"))
            current = ARMEL;
        else if (arch.startsWith("armhf") || arch.equals("armeabiv7a") || arch.equals("armv7") || arch.equals("armv8l"))
            current = ARMHF;
        else if (arch.equals("aarch64") || arch.startsWith("arm64") || arch.equals("armv8") || arch.equals("armv8a"))
            current = AARCH64;
        else if (arch.equals("loonarch64"))
            current = LOONGARCH64;
        else if (arch.equals("sw64"))
            current = SW64;
        else if (arch.equals("riscv") || arch.equals("riscv32"))
            current = RISCV;
        else if (arch.equals("riscv64"))
            current = RISCV64;
        else if (arch.equals("mips") || arch.equals("mips32"))
            current = MIPS;
        else if (arch.equals("mipsel") || arch.equals("mips32el"))
            current = MIPSEL;
        else if (arch.equals("mips64"))
            current = MIPS64;
        else if (arch.equals("mips64el"))
            current = MIPSEL64;
        else if (arch.equals("ppc") || arch.equals("ppc32"))
            current = PPC;
        else if (arch.equals("ppcle") || arch.equals("ppc32le"))
            current = PPCLE;
        else if (arch.equals("ppc64"))
            current = PPC64;
        else if (arch.equals("ppc64le"))
            current = PPC64LE;
        else if (arch.equals("sparc") || arch.equals("sparc32"))
            current = SPARC;
        else if (arch.equals("sparcv9") || arch.equals("sparc64"))
            current = SPARC64;
        else if (arch.equals("s390"))
            current = S390;
        else if (arch.equals("s390x"))
            current = S390X;

        CURRENT = current;
    }

    private final String value;

    Arch(String v) {
        value = v;
    }

    @Override
    public String toString() {
        return value;
    }
}