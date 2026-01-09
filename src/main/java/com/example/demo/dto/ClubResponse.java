package com.example.demo.dto;

public class ClubResponse {

    private Long id;
    private String name;
    private String description;
    private String sportType;
    private String avatarUrl;
    private int memberCount;
    private boolean joined;

    public ClubResponse(
            Long id,
            String name,
            String description,
            String sportType,
            String avatarUrl,
            int memberCount,
            boolean joined
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sportType = sportType;
        this.avatarUrl = avatarUrl;
        this.memberCount = memberCount;
        this.joined = joined;
    }

    // ===== getter =====

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSportType() {
        return sportType;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public boolean isJoined() {
        return joined;
    }
}
