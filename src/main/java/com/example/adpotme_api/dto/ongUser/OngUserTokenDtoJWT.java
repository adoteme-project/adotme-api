package com.example.adpotme_api.dto.ongUser;

public record OngUserTokenDtoJWT(String token, String refreshToken, Long ongUserId, Long ongId,
                                 com.example.adpotme_api.entity.ongUser.Role role) {
}
