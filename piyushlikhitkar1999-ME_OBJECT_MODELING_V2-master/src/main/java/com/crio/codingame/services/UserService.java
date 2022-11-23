package com.crio.codingame.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.crio.codingame.dtos.UserRegistrationDto;
import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.ContestStatus;
import com.crio.codingame.entities.RegisterationStatus;
import com.crio.codingame.entities.ScoreOrder;
import com.crio.codingame.entities.User;
import com.crio.codingame.exceptions.ContestNotFoundException;
import com.crio.codingame.exceptions.InvalidOperationException;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.codingame.repositories.IContestRepository;
import com.crio.codingame.repositories.IUserRepository;
import com.crio.codingame.repositories.UserRepository;

class ScoreOrderAsc implements Comparator<User>
{
    public int compare(User a,User b)
    {
        return a.getScore().compareTo(b.getScore());
    }
}

class ScoreOrderDesc implements Comparator<User>
{
    public int compare(User a,User b)
    {
        return b.getScore().compareTo(a.getScore());
    }
}

public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IContestRepository contestRepository;

    public UserService(IUserRepository userRepository, IContestRepository contestRepository) {
        this.userRepository = userRepository;
        this.contestRepository = contestRepository;
    }
    // TODO: CRIO_TASK_MODULE_SERVICES
    // Create and store User into the repository.
    @Override
    public User create(String name) {
        User user=new User("1",name,0);
        userRepository.save(user);
        return user;
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Get All Users in Ascending Order w.r.t scores if ScoreOrder ASC.
    // Or
    // Get All Users in Descending Order w.r.t scores if ScoreOrder DESC.

    @Override
    public List<User> getAllUserScoreOrderWise(ScoreOrder scoreOrder){
     List<User> list=userRepository.findAll();
     if (scoreOrder.equals(ScoreOrder.ASC)) {
        Collections.sort(list,new ScoreOrderAsc());
     }
     else
     {
        Collections.sort(list,new ScoreOrderDesc());
     }
     return list;
    }

    @Override
    public UserRegistrationDto attendContest(String contestId, String userName) throws ContestNotFoundException, UserNotFoundException, InvalidOperationException {
        Contest contest = contestRepository.findById(contestId).orElseThrow(() -> new ContestNotFoundException("Cannot Attend Contest. Contest for given id:"+contestId+" not found!"));
        User user = userRepository.findByName(userName).orElseThrow(() -> new UserNotFoundException("Cannot Attend Contest. User for given name:"+ userName+" not found!"));
        if(contest.getContestStatus().equals(ContestStatus.IN_PROGRESS)){
            throw new InvalidOperationException("Cannot Attend Contest. Contest for given id:"+contestId+" is in progress!");
        }
        if(contest.getContestStatus().equals(ContestStatus.ENDED)){
            throw new InvalidOperationException("Cannot Attend Contest. Contest for given id:"+contestId+" is ended!");
        }
        if(user.checkIfContestExists(contest)){
            throw new InvalidOperationException("Cannot Attend Contest. Contest for given id:"+contestId+" is already registered!");
        }
        user.addContest(contest);
        userRepository.save(user);
        return new UserRegistrationDto(contest.getName(), user.getName(),RegisterationStatus.REGISTERED);
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Withdraw the user from the contest
    // Hint :- Refer Unit Testcases withdrawContest method

    @Override
    public UserRegistrationDto withdrawContest(String contestId, String userName) throws ContestNotFoundException, UserNotFoundException, InvalidOperationException {
        Contest c=contestRepository.findById(contestId).orElseThrow(() -> new ContestNotFoundException());
        User u=userRepository.findByName(userName).orElseThrow(() -> new UserNotFoundException());
        
        
        if (c.getContestStatus().equals(ContestStatus.ENDED) || c.getCreator().equals(u.getName())
    || c.getContestStatus().equals(ContestStatus.IN_PROGRESS )) {
            throw new InvalidOperationException();
        }

        // if(!u.checkIfContestExists(c) && c.getContestStatus().equals(ContestStatus.NOT_STARTED))
        // {
        //     throw new InvalidOperationException();
        // }

        if(!u.checkIfContestExists(c))
        {
        throw new InvalidOperationException();
        }
        try {
            u.deleteContest(c);
        } catch (Exception e) {
            throw new ContestNotFoundException();
        }

        UserRegistrationDto user=new UserRegistrationDto(c.getName(), u.getName(), RegisterationStatus.NOT_REGISTERED);
        User u1=userRepository.save(u);
        return user;
    }
    
}
