package com.example.rqs.core.spacemember.service;

import com.example.rqs.core.spacemember.service.dtos.*;

public interface SpaceMemberUpdateService {
    SpaceMemberResponse changeSpaceMemberRole(UpdateSpaceMemberRole updateSpaceMemberRole);
    void deleteSpaceMember(DeleteSpaceMember deleteSpaceMember);
}
