package spring.natanel.fightwaybackend.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import spring.natanel.fightwaybackend.error.BadRequestException;


import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

//https://github.com/jwtk/jjwt#quickstart
@Component
public class JWTProvider {
    //read values from application.properties

    @Value("${spring.natanel.blog.secret}")
    private String secret;

    @Value("${spring.natanel.blog.expires}")
    private Long expires;

    //1) KEY
    private static Key mySecterKey;
    @PostConstruct //will activate func automatically after the constructor
    private void init(){

    //JWT need to get a key using HMAC algo so we convert the "secret" to byte[] providing it for hmacShaKeyFor and it generate a secret key
        mySecterKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    //2) generate JWT (PAYLOAD-Data-)
    public String generateToken(String username){
        var currentDate = new Date();
        var expiresDate = new Date(currentDate.getTime() + expires);

        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expiresDate)
                .signWith(mySecterKey)
                .compact();
    }



    //3) read from JWT
    public boolean validateToken(String jwt){
        try{
            //verifies that our secretKey(that built with signature) is similar to the provided signed jwt string
            Jwts.parser().
                    verifyWith((SecretKey) mySecterKey)
                    .build() //building the key
                    .parseSignedClaims(jwt);
        }
        catch (ExpiredJwtException e){
            throw new BadRequestException("Token", "Expired");
        }
        catch (MalformedJwtException e){
            throw new BadRequestException("Token", "Invalid");
        }
        catch (JwtException e){
            throw new BadRequestException("Token", "Exception " + e.getMessage());
        }

        return true;
    }

    //getUsername from JWT Token
    public String getUsernameFromToken(String jwt){
        return Jwts.parser().
                verifyWith((SecretKey) mySecterKey)
                .build()
                .parseSignedClaims(jwt)
                .getBody().getSubject();
    }
}
