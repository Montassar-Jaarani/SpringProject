package com.example.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Choice;
import com.example.entity.Poll;
import com.example.entity.User;
import com.example.repository.OptionRepository;
import com.example.repository.PollRepository;
import com.example.repository.UserRepository;


@Service
public class PollService {
	
	private final PollRepository pollRepository;
    private final OptionRepository optionRepository;
    private final UserRepository userRepository;

    @Autowired
    public PollService(PollRepository pollRepository, OptionRepository optionRepository, UserRepository userRepository) {
        this.pollRepository = pollRepository;
        this.optionRepository = optionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String savePoll(Poll poll, String userName) {

        User user = userRepository.findByUserName(userName).get();
        	System.err.println(user.getId());
        poll.setUser(user);
        Poll savedPoll = pollRepository.save(poll);
        poll.getOptions().stream().forEach(option -> {
            option.setPoll(savedPoll);
            optionRepository.save(option);
        });

        return "added";
    }

    @Transactional
    public Poll updatePoll(Poll poll) {
        optionRepository.deleteByPollId(poll.getId());

        Poll savedPoll = pollRepository.save(poll);
        poll.getOptions().stream().forEach(option -> {
            option.setPoll(savedPoll);
            optionRepository.save(option);
        });

        return savedPoll;
    }

    public List<Poll> getAll() {
        return pollRepository.findAll();
    }

    public Poll getPollById(Long id) {
        return pollRepository.getOne(id);
    }

    public void deletePollById(Long id) {
        pollRepository.deleteById(id);
    }

    @Transactional
    public String vote(Long id, Long optionId, int idUser)  {
        Poll poll = pollRepository.getOne(id);
        User u = new User();
      //u =  userRepository.findById(idUser).get();
      u =  userRepository.findById(idUser).get();
        //TO-DO: check end-date
        if (poll.getEndDate().before(new Date())) {
           return "Voting has already ended!";
        }
        //TO-DO: add IP
        List<String> ipAdresses = poll.getIpAdresses();
        if (ipAdresses.contains(u.getUserName())) {
        	 return "You can only vote once!";
        }
        List<Choice> options = poll.getOptions().stream().filter(option -> Objects.equals(option.getId(), optionId)).collect(Collectors.toList());
        if (options.size() == 1) {
        	Choice option = options.get(0);
            option.setScore(option.getScore() + 1);
            optionRepository.save(option);
            poll.getIpAdresses().add(u.getUserName());
            pollRepository.save(poll);
        } else {
        	 //return "Option id for poll not unique!";
        	 return "Thank you for your vote!";
        }
        return "Option id for poll not unique!";
    }

    public List<Poll> getAllForUser(String userName) {
        User user = userRepository.findOneByUserName(userName);
        return pollRepository.findAllByUser(user);
    }

    public List<Poll> getAllVisibleForUser(String userName) {
        User user = userRepository.findOneByUserName(userName);

        return pollRepository.findAllByUserAndVisible(user, true);
    }

}
