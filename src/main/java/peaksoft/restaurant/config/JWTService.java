//package peaksoft.restaurant.config;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import io.jsonwebtoken.JwtException;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import peaksoft.restaurant.entities.User;
//import peaksoft.restaurant.repo.UserRepo;
//
//
//import java.time.ZonedDateTime;
//
//@Service
//public class JWTService {
//
//    @Value("${security.secret.key}")
//    private String secretKey;
//    private final UserRepo  userRepo;
//
//    public JWTService(UserRepo  userRepo) {
//        this.userRepo = userRepo;
//    }
//
//    public String generateToken(User user) {
//        ZonedDateTime zonedDateTime = ZonedDateTime.now();
//        return  JWT.create()
//                .withSubject(user.getEmail())
//                .withClaim("role", user.getRole().name())
//                .withClaim("id", user.getId())
//                .withIssuedAt(zonedDateTime.toInstant())
//                .withExpiresAt(zonedDateTime.plusDays(7).toInstant())
//                .sign(getAlgorithm());
//    }
//
//    public User verifyToken(String token) {
//        Algorithm algorithm = getAlgorithm();
//        JWTVerifier verifier = JWT.require(algorithm).build();
//        DecodedJWT decodedJWT = verifier.verify(token);
//        String email = decodedJWT.getSubject();
//        return userRepo.findByEmail(email)
//                .orElseThrow(
//                        () -> new JwtException(
//                                "JWT token does not contain email: " + email
//                        )
//                );
//    }
//
//    private Algorithm getAlgorithm() {
//        return Algorithm.HMAC256(secretKey);
//    }
//}
