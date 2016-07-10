package net.eatcode.trainwatch.movement.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class EhcacheSpike {

    public static void main(String[] args) {

        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("refdata",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, String.class,
                                ResourcePoolsBuilder.heap(10)))
                .build(true);

        Cache<Integer, String> refdata = cacheManager.getCache("refdata", Integer.class, String.class);

        refdata.put(1, "mummy");
        refdata.put(2, "daddy");
        
        System.out.println(refdata.get(1));
        System.out.println(refdata.get(2));
        System.out.println(refdata.get(3));

    }
}
