package com.tp;

import org.springframework.stereotype.Service;

@Service
public class ReplayService {
    private final ReplayRepository replayRepository;

    public ReplayService(ReplayRepository replayRepository){
        this.replayRepository = replayRepository;
    }

    public Replay saveReplay(Replay r) {
        return replayRepository.save(r);
    }

    public Iterable<Replay> getAllUsers() {
        return replayRepository.findAll();
    }

    public Replay getReplayById(Long id) {
        return replayRepository.findById(id).orElse(null);
    }

    public void deleteUser(Long id) {
        replayRepository.deleteById(id);
    }

}
