

package com.mts.spring;


import static com.mts.common.MetroTransisConstants.*;

import com.mts.handler.NextBusServiceHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;



@Configuration
@ComponentScan(basePackages = "com.mts")
public class MetroTransitServiceHandlerConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetroTransitServiceHandlerConfiguration.class);

    @Bean
    public RouterFunction<ServerResponse> routeHelloWorld(NextBusServiceHandler nextBusServiceHandler) {
        return RouterFunctions.route(RequestPredicates.POST(API_TRIPS + API_NEXTBUS)
                                                      .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                    nextBusServiceHandler::getNextTrip);
    }


    // @Bean
    // public org.springframework.cache.CacheManager jCacheManager() {
    // return new JCacheCacheManager(cacheManager());
    // }
    //
    // private CacheManager cacheManager() {
    // CacheManager cm =
    // javax.cache.Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider").getCacheManager();
    // cm.createCache("trips",
    // createCacheMutableConfiguration(createCacheEntryCreatedListener("trips"),
    // createCacheEntryExpiredListener("trips"),
    // createCacheEntryRemovedListener("trips"), createCacheEntryUpdatedListener("trips"), Duration.FIVE_MINUTES,
    // true));
    // return cm;
    // }
    //
    // // cache entry listener
    // public CacheEntryCreatedListener<Object, Object> createCacheEntryCreatedListener(final String cacheName) {
    // final CacheEntryCreatedListener<Object, Object> listenerCreated = new CacheEntryCreatedListener<Object, Object>()
    // {
    // @Override
    // public void onCreated(final Iterable<CacheEntryEvent<? extends Object, ? extends Object>> events) throws
    // CacheEntryListenerException {
    // LOGGER.info(String.format("CACHE_ENTRY_CREATED_LISTENER CACHE_NAME=%s", cacheName));
    // }
    // };
    // return listenerCreated;
    // }
    //
    // // cache expire listener
    // public CacheEntryExpiredListener<Object, Object> createCacheEntryExpiredListener(final String cacheName) {
    // final CacheEntryExpiredListener<Object, Object> listenerExpired = new CacheEntryExpiredListener<Object, Object>()
    // {
    // @Override
    // public void onExpired(final Iterable<CacheEntryEvent<? extends Object, ? extends Object>> events) throws
    // CacheEntryListenerException {
    // LOGGER.info(String.format("CACHE_EXPIRED_LISTENER CACHE_NAME=%s", cacheName));
    // }
    // };
    // return listenerExpired;
    // }
    //
    // // cache remove listener
    // public CacheEntryRemovedListener<Object, Object> createCacheEntryRemovedListener(final String cacheName) {
    // final CacheEntryRemovedListener<Object, Object> listenerRemoved = new CacheEntryRemovedListener<Object, Object>()
    // {
    // @Override
    // public void onRemoved(final Iterable<CacheEntryEvent<? extends Object, ? extends Object>> events) throws
    // CacheEntryListenerException {
    // LOGGER.info(String.format("CACHE_REMOVED_LISTENER CACHE_NAME=%s", cacheName));
    // }
    // };
    // return listenerRemoved;
    // }
    //
    // // cache update listener
    // public CacheEntryUpdatedListener<Object, Object> createCacheEntryUpdatedListener(final String cacheName) {
    // final CacheEntryUpdatedListener<Object, Object> listenerUpdated = new CacheEntryUpdatedListener<Object, Object>()
    // {
    // @Override
    // public void onUpdated(final Iterable<CacheEntryEvent<? extends Object, ? extends Object>> events) throws
    // CacheEntryListenerException {
    // }
    // };
    // return listenerUpdated;
    // }
    //
    //
    // @SuppressWarnings({ "unchecked", "rawtypes" })
    // private static MutableConfiguration<Object, Object> createCacheMutableConfiguration(final
    // CacheEntryCreatedListener<Object, Object> listenerCreated,
    // final CacheEntryExpiredListener<Object, Object> listenerExpired,
    // final CacheEntryRemovedListener<Object, Object> listenerRemoved,
    // final CacheEntryUpdatedListener<Object, Object> listenerUpdated,
    // final Duration cacheExpiryTime, final Boolean managementEnabled) {
    // return new MutableConfiguration<>().setTypes(Object.class, Object.class)
    // .setExpiryPolicyFactory(cacheExpiryTime != null ? CreatedExpiryPolicy.factoryOf(cacheExpiryTime)
    // : EternalExpiryPolicy.factoryOf())
    // .setStatisticsEnabled(true).setManagementEnabled(managementEnabled).setStoreByValue(false)
    // .addCacheEntryListenerConfiguration(
    // new MutableCacheEntryListenerConfiguration<>(new FactoryBuilder.SingletonFactory(listenerCreated), null,
    // true, true))
    // .addCacheEntryListenerConfiguration(
    // new MutableCacheEntryListenerConfiguration<>(new FactoryBuilder.SingletonFactory(listenerExpired), null,
    // true, true))
    // .addCacheEntryListenerConfiguration(
    // new MutableCacheEntryListenerConfiguration<>(new FactoryBuilder.SingletonFactory(listenerRemoved), null,
    // true, true))
    // .addCacheEntryListenerConfiguration(
    // new MutableCacheEntryListenerConfiguration<>(new FactoryBuilder.SingletonFactory(listenerUpdated), null,
    // true, true));
    // }

}
