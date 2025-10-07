// 代码生成时间: 2025-10-07 22:11:42
package com.example.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import play.Logger;

/**
 * A utility class to perform DNS resolution and caching.
 */
public class DnsResolverCache {

    private static final Logger.ALogger logger = Logger.of(DnsResolverCache.class);
    private final Map<String, String> cache;

    public DnsResolverCache() {
        this.cache = new HashMap<>();
    }

    /**
     * Resolves a hostname to an IP address using DNS.
     * If the hostname is already cached, returns the cached IP address.
     *
     * @param hostname The hostname to resolve.
     * @return The resolved IP address.
     */
    public String resolveHostname(String hostname) {
        if (cache.containsKey(hostname)) {
            return cache.get(hostname);
        } else {
            try {
                String ip = InetAddress.getByName(hostname).getHostAddress();
                cache.put(hostname, ip);
                return ip;
            } catch (UnknownHostException e) {
                logger.error("Failed to resolve hostname: " + hostname, e);
                throw new RuntimeException("Failed to resolve hostname: " + hostname, e);
            }
        }
    }

    /**
     * Clears the DNS cache.
     */
    public void clearCache() {
        cache.clear();
    }

    public static void main(String[] args) {
        DnsResolverCache dnsResolverCache = new DnsResolverCache();

        // Example usage
        String hostname = "www.google.com";
        try {
            String ipAddress = dnsResolverCache.resolveHostname(hostname);
            System.out.println("Resolved IP Address for " + hostname + ": " + ipAddress);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
