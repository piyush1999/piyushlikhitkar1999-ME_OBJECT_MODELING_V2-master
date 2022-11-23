package com.crio.codingame.commands;

import java.util.List;

import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.Level;
import com.crio.codingame.entities.User;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.codingame.repositories.UserRepository;
import com.crio.codingame.services.IContestService;

public class CreateContestCommand implements ICommand{

    private final IContestService contestService;

    public CreateContestCommand(IContestService contestService) {
        this.contestService = contestService;
    }

    // TODO: CRIO_TASK_MODULE_CONTROLLER
    // Execute create method of IContestService and print the result.
    // Also Handle Exceptions and print the error messsages if any.
    // Look for the unit tests to see the expected output.
    // Sample Input Token List:- ["CREATE_CONTEST","CRIODO2_CONTEST","LOW Monica","40"]
    // or
    // ["CREATE_CONTEST","CRIODO1_CONTEST","HIGH","Ross"]
    // Hint - Use Parameterized Exceptions in the Service class to match with the Unit Tests Output.

    @Override
    public void execute(List<String> tokens) {
        //UserRepository userRepository=new UserRepository();
        //final User user = userRepository.findByName(tokens.get(3)).orElseThrow(() -> new UserNotFoundException("Contest Creator for given name: creator not found!"));
        // Level level=Level.valueOf(tokens.get(2));
        // try {
        //     Contest contest=contestService.create(tokens.get(1),level ,tokens.get(3), Integer.valueOf(tokens.get(4)));
            
        // } catch (Exception e) {
           
        // }
        
        // System.out.println( contest.toString());
        try{
            int tokenSize = tokens.size();
            String contestName = tokens.get(1);
            Level level = Level.valueOf(tokens.get(2));
            String contestCreator = tokens.get(3);
            Contest contest = null;

            if(tokenSize == 5){
                Integer numQuestions = Integer.parseInt(tokens.get(4));
                contest = contestService.create(contestName, level, contestCreator, numQuestions);       
            }
else{
                contest = contestService.create(contestName, level, contestCreator, null);
            }
            System.out.println(contest.toString());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        

    }
    
}
