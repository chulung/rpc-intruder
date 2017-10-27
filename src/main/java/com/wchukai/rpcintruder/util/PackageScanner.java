package com.wchukai.rpcintruder.util;

/**
 * Created by chukai on 2017/10/25.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chukai
 */
public class PackageScanner {
    private static final Logger LOGGER = LoggerFactory.getLogger(PackageScanner.class);
    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    /**
     * Get all fully qualified names located in the specified package
     * and its sub-package.
     *
     * @param basePackage the base package
     * @return A list of fully qualified names.
     */
    public Set<String> scan(String basePackage){
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
        Set<String> clazzSet = new HashSet<String>();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                org.springframework.util.ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) + "/" + DEFAULT_RESOURCE_PATTERN;
        try {
            Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
            for (Resource resource : resources) {
                //检查resource，这里的resource都是class
                String clazz = loadClassName(metadataReaderFactory, resource);
                if (clazz != null) {
                    clazzSet.add(clazz);
                }
            }
        } catch (Exception e) {
            LOGGER.error("获取包下面的类信息失败,package:" + basePackage, e);
        }

        return clazzSet;
    }

    /**
     * 加载资源，根据resource获取className
     *
     * @param metadataReaderFactory spring中用来读取resource为class的工具
     * @param resource              这里的资源就是一个Class
     * @throws IOException
     */
    private static String loadClassName(MetadataReaderFactory metadataReaderFactory, Resource resource) throws IOException {
        if (resource.isReadable()) {
            MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
            if (metadataReader != null) {
                return metadataReader.getClassMetadata().getClassName();
            }
        }
        return null;
    }
}
