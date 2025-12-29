package com.example.demo.service;

import com.example.demo.model.Club;
import com.example.demo.model.Users;
import com.example.demo.repository.ClubRespository;
import com.example.demo.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClubService {

    private final ClubRespository clubRespository;
    private final UsersRepository usersRepository;
    private final CloudinaryService cloudinaryService;

    public ClubService(
            ClubRespository clubRespository,
            UsersRepository usersRepository,
            CloudinaryService cloudinaryService
    ) {
        this.clubRespository = clubRespository;
        this.usersRepository = usersRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public Club createClub(
            Long userId,
            String name,
            String description,
            String sportType,
            String organizationType,
            MultipartFile avatar
    ) throws Exception {

        Users owner = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        Club club = new Club();
        club.setName(name);
        club.setDescription(description);
        club.setSportType(sportType);
        club.setOrganizationType(organizationType);
        club.setOwner(owner);

        // upload avatar nếu có
        if (avatar != null && !avatar.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(avatar);
            club.setAvatarUrl(imageUrl);
        }

        return clubRespository.save(club);
    }
}
