package com.bridee.api.entity.enums;

import com.bridee.api.exception.CloudProviderNotFound;

public enum CloudProvider {
    AWS("AWS"), AZURE("AZURE");

    private final String valor;

    CloudProvider(String valor) {
        this.valor = valor;
    }

    public static CloudProvider getCloudProvider(String cloudProviderName){
        if(cloudProviderIsNotAvailable(cloudProviderName)){
            throw new CloudProviderNotFound();
        }
        return CloudProvider.valueOf(cloudProviderName);
    }

    private static boolean cloudProviderIsNotAvailable(String cloudProviderName){
        CloudProvider cloudProvider = CloudProvider.valueOf(cloudProviderName);
        return !cloudProvider.valor.equals("AWS") && !cloudProvider.valor.equals("AZURE");
    }


}
