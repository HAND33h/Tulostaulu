package com.hand33h.tulostaulu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Canonical public source map for systematic Gen1-17 hero verification. */
public final class HeroGenerationSourceRegistry {
    public static final String CROSS_CHECK_ROSTER = "https://wos-wiki.de/en/helden/";
    public static final String CROSS_CHECK_HEROES = "https://wiki.wosforge.org/wiki/Heroes";
    public static final String VERIFIED_THROUGH = "2026-09-09";
    public static final String VERIFICATION_STATE = "PARTIALLY_VERIFIED";
    public static final class Source {
        public final int generation;
        public final String url;
        Source(int g,String u){generation=g;url=u;}
    }
    public static final List<Source> GENERATIONS=Collections.unmodifiableList(Arrays.asList(
        new Source(1,"https://wosheroes.com/"),
        new Source(2,"https://wosheroes.com/heroes/generation-2-heroes/"),
        new Source(3,"https://wosheroes.com/heroes/generation-3-heroes/"),
        new Source(4,"https://wosheroes.com/heroes/generation-4-heroes/"),
        new Source(5,"https://wosheroes.com/heroes/generation-5-heroes/"),
        new Source(6,"https://wosheroes.com/heroes/generation-6-heroes/"),
        new Source(7,"https://wosheroes.com/heroes/generation-7-heroes/"),
        new Source(8,"https://wosheroes.com/heroes/generation-8-heroes/"),
        new Source(9,"https://wosheroes.com/heroes/generation-9-heroes/"),
        new Source(10,"https://wosheroes.com/heroes/generation-10-heroes/"),
        new Source(11,"https://wosheroes.com/heroes/generation-11-heroes/"),
        new Source(12,"https://wosheroes.com/heroes/generation-12-heroes/"),
        new Source(13,"https://wosheroes.com/heroes/generation-13-heroes/"),
        new Source(14,"https://wosheroes.com/heroes/generation-14-heroes/"),
        new Source(15,"https://wosheroes.com/heroes/generation-15-heroes/"),
        new Source(16,"https://wosheroes.com/heroes/generation-16-heroes/"),
        new Source(17,"https://wosheroes.com/heroes/generation-17-heroes/")
    ));
    private HeroGenerationSourceRegistry(){}
}
