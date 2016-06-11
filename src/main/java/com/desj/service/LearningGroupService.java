package com.desj.service;

import com.desj.model.LearningGroup;
import com.desj.model.LearningGroupRepository;
import com.desj.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julien on 23.05.16.
 */
@Service
@Transactional
public class LearningGroupService {

    @Autowired
    LearningGroupRepository learningGroupRepository;

    public List<User> getAllMemberOfLearningGroup(Integer learningGroupId) {
        return learningGroupRepository.findOne(learningGroupId).getMembers();
    }

    public void addMemberToLearningGroup(int learningGroupId, User user) {
        List<User> memberList = new ArrayList<>();
        memberList.addAll(getAllMemberOfLearningGroup(learningGroupId));
        memberList.add(user);
        learningGroupRepository.findOne(learningGroupId).setMembers(memberList);
    }

    /**
     * Helps to find all learning groups, which the user isn't a member of yet
     * @return all learning groups, the current user isn't a member of
     */
    public List<LearningGroup> getNewLearningGroups(User user) {

        List<LearningGroup> newLearningGroups = new ArrayList<>();

        for(LearningGroup learningGroup : learningGroupRepository.findAll()) {
            if(!learningGroup.getMembers().contains(user)) {
                newLearningGroups.add(learningGroup);
            }
        }
        return newLearningGroups;
    }

    public void save(LearningGroup learningGroup, User user) {

        List<User> userList = new ArrayList<>();
        userList.add(user);
        learningGroup.setMembers(userList);
        learningGroupRepository.save(learningGroup);
    }
}
