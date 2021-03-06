package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/home/credential")
public class CredentialController {

    private final UserService userService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public CredentialController(UserService userService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/upload")
    public String uploadCredential(Authentication auth, @ModelAttribute("newCredential") Credential credential, Model model) {
        boolean saveErrorMsg = false;
        String uploadErrorMsg = null;

        credential.setUrl(credential.getUrl().trim());
        credential.setUsername(credential.getUsername().trim());
        credential.setPassword(credential.getPassword().trim());

        // check if credential duplicate
        List<Credential> credentialList = credentialService.getCredentialList(userService.getUser(auth.getName()).getUserId());
        for (Credential element : credentialList) {
            if (credential.getUrl().matches(element.getUrl())
                    && credential.getUsername().matches(element.getUsername())
                    && credential.getPassword().matches(encryptionService.decryptValue(element.getPassword(),element.getKey()))) {

                // check if it's creating or editing
                if (credential.getCredentialId() == null) {
                    // create credential
                    uploadErrorMsg = "The credential already exists.";
                } else {
                    // update credential
                    if (Objects.equals(credential.getCredentialId(), element.getCredentialId())) {
                        uploadErrorMsg = "No changes have been made.";
                    } else {
                        uploadErrorMsg = "The credential already exists.";
                    }
                }
            }
        }

        if (uploadErrorMsg == null) {
            if (credential.getCredentialId() == null) {
                // create credential
                int rowsAdded = credentialService.createCredential(credential);
                if (rowsAdded < 0) saveErrorMsg = true;
            } else {
                // update credential
                credentialService.updateCredential(credential);
            }
        }

        if (uploadErrorMsg == null) {
            if (saveErrorMsg) {
                model.addAttribute("saveErrorMsg",true);
            } else {
                model.addAttribute("success",true);
            }
        } else {
            model.addAttribute("uploadErrorMsg", uploadErrorMsg);
        }

        return "result";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteNote(@PathVariable("credentialId") Integer credentialId, Model model) {
        credentialService.deleteCredential(credentialId);
        model.addAttribute("success",true);
        return "result";
    }
}
