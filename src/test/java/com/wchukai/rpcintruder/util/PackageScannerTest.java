package com.wchukai.rpcintruder.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author chukai
 */
public class PackageScannerTest {
    @Test
    public void getFullyQualifiedClassNameList() throws Exception {
        PackageScanner scan = new PackageScanner();
        assertThat(scan.scan("com.wchukai.rpcintruder.util")).contains("com.wchukai.rpcintruder.util.PackageScannerTest").contains("com.wchukai.rpcintruder.util.sub.SubPackageClass");
    }

}