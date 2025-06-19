package org.wso2.carbon.test;

import org.osgi.service.component.annotations.Component;
import org.wso2.carbon.apimgt.common.gateway.dto.JWTInfoDto;
import org.wso2.carbon.apimgt.common.gateway.jwtgenerator.APIMgtGatewayJWTGeneratorImpl;
import org.wso2.carbon.apimgt.common.gateway.jwtgenerator.AbstractAPIMgtGatewayJWTGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * Test
 */
@Component(
        enabled = true,
        service = AbstractAPIMgtGatewayJWTGenerator.class,
        name = "customgatewayJWTGenerator"
)
public class CustomGatewayJWTGenerator extends APIMgtGatewayJWTGeneratorImpl {

    @Override
    public Map<String, Object> populateStandardClaims(JWTInfoDto jwtInfoDto) {
        Map<String, Object> claims = new HashMap<>();
        String dialect = this.getDialectURI();

        long currentTime = System.currentTimeMillis();
        long expireIn = currentTime + super.jwtConfigurationDto.getTTL() * 1000L;
        claims.put("iss", "wso2.org/products/am");
        claims.put("exp", String.valueOf(expireIn));
        claims.put("iat", String.valueOf(currentTime));

        claims.put("enduserTenantId", String.valueOf(jwtInfoDto.getEndUserTenantId()));
        return claims;
    }

    @Override
    public Map<String, Object> populateCustomClaims(JWTInfoDto jwtInfoDto) {
        return new HashMap<>();
    }
}
