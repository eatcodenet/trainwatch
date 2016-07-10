package net.eatcode.trainwatch.movement.ehcache;

import java.net.URI;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.PersistentCacheManager;
import org.ehcache.clustered.client.config.builders.ClusteredResourcePoolBuilder;
import org.ehcache.clustered.client.config.builders.ClusteringServiceConfigurationBuilder;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

public class EhcacheSpike {

    public static void main(String[] args) {

        CacheManagerBuilder<PersistentCacheManager> builder = CacheManagerBuilder.newCacheManagerBuilder()
                .with(ClusteringServiceConfigurationBuilder
                        .cluster(URI.create("terracotta://localhost:9510/trainwatch"))
                        .autoCreate()
                        .defaultServerResource("primary-server-resource"))
                .withCache("cache2",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, String.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .with(ClusteredResourcePoolBuilder.clusteredDedicated("primary-server-resource",
                                                32, MemoryUnit.MB))));

        PersistentCacheManager cacheManager = builder.build(true);

        Cache<Integer, String> cache = cacheManager.getCache("cache2", Integer.class, String.class);
        cache.put(1, "alpha" + System.currentTimeMillis());

        System.out.println(cache.get(1));
        cacheManager.close();
        System.out.println("DONE");
    }
}
