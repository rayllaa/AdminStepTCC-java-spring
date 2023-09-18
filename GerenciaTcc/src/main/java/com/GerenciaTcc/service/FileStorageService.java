package com.GerenciaTcc.service;

import com.GerenciaTcc.exception.BadRequestException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Verifique se o arquivo com o mesmo nome já existe
        if (fileExists(originalFileName)) {
            originalFileName = generateUniqueFileName(originalFileName);
        }

        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            Path targetLocation = uploadPath.resolve(originalFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return originalFileName;
        } catch (IOException ex) {
            throw new BadRequestException("Não foi possível armazenar o arquivo " + originalFileName + ". Tente novamente." + ex);
        }
    }

    private boolean fileExists(String fileName) {
        Path filePath = Paths.get(uploadDir).resolve(fileName).toAbsolutePath().normalize();
        return Files.exists(filePath);
    }

    private String generateUniqueFileName(String originalFileName) {
        String baseName = FilenameUtils.getBaseName(originalFileName);
        String extension = FilenameUtils.getExtension(originalFileName);
        int counter = 1;

        while (true) {
            String newFileName = baseName + "_" + counter + "." + extension;
            if (!fileExists(newFileName)) {
                return newFileName;
            }
            counter++;
        }
    }

    public Resource getFile(String filePath) {
        try {
            Path file = Paths.get(uploadDir).resolve(filePath).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("Arquivo não encontrado: " + filePath);
            }
        } catch (MalformedURLException ex) {
            throw new BadRequestException("Erro ao recuperar o arquivo: " + filePath + "Excecao: "+ ex);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
