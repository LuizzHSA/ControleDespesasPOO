package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class CriptografiaUtil {
    
    private static final String ALGORITHM = "SHA-256";
    
    /**
     * Gera um hash SHA-256 da senha fornecida
     * @param senha A senha a ser criptografada
     * @return O hash da senha em formato Base64
     */
    public static String criptografarSenha(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            byte[] hash = digest.digest(senha.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao criptografar senha", e);
        }
    }
    
    /**
     * Verifica se a senha fornecida corresponde ao hash armazenado
     * @param senha A senha em texto plano
     * @param hashArmazenado O hash armazenado para comparação
     * @return true se a senha corresponder ao hash, false caso contrário
     */
    public static boolean verificarSenha(String senha, String hashArmazenado) {
        String hashSenha = criptografarSenha(senha);
        return hashSenha.equals(hashArmazenado);
    }
    
    /**
     * Gera uma senha aleatória
     * @param tamanho O tamanho da senha a ser gerada
     * @return Uma senha aleatória
     */
    public static String gerarSenhaAleatoria(int tamanho) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        SecureRandom random = new SecureRandom();
        StringBuilder senha = new StringBuilder();
        
        for (int i = 0; i < tamanho; i++) {
            int index = random.nextInt(caracteres.length());
            senha.append(caracteres.charAt(index));
        }
        
        return senha.toString();
    }
    
    /**
     * Valida se a senha atende aos critérios mínimos de segurança
     * @param senha A senha a ser validada
     * @return true se a senha for válida, false caso contrário
     */
    public static boolean validarSenha(String senha) {
        if (senha == null || senha.length() < 6) {
            return false;
        }
        
        boolean temMaiuscula = senha.chars().anyMatch(Character::isUpperCase);
        boolean temMinuscula = senha.chars().anyMatch(Character::isLowerCase);
        boolean temNumero = senha.chars().anyMatch(Character::isDigit);
        
        return temMaiuscula && temMinuscula && temNumero;
    }
}
