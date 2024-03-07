package com.akoele.graphqltraining.service;

import com.akoele.graphqltraining.model.Player;
import com.akoele.graphqltraining.model.Team;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PlayerService {
    private List<Player> playerList = new ArrayList<>();

    AtomicInteger id = new AtomicInteger(0);

    public List<Player> findAll(){
        return playerList;
    }

    public Optional<Player> findOne(Integer id){
        return playerList.stream()
                .filter(player -> player.id() == id)
                .findFirst();
    }

    public Player create(String name, Team team){
        Player player = new Player(id.incrementAndGet(),name,team);
        playerList.add(player);
        return player;
    }

    public Player delete(Integer id){
        Player player = playerList.stream()
                .filter(player1 -> player1.id() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid player"));
        playerList.remove(player);
        return player;
    }

    public Player update(Integer id,String name, Team team){
        Player updatedplayer = new Player(id,name,team);
        Optional<Player> optional = playerList.stream()
                .filter(player1 -> player1.id() == id)
                .findFirst();
        if(optional.isPresent()){
            Player player = optional.get();
            int index = playerList.indexOf(player);
            playerList.set(index,updatedplayer);
        }else {
            throw new IllegalArgumentException("Invalid Player");
        }
        return updatedplayer;
    }

    @PostConstruct
    private void init(){
        playerList.add(new Player(id.incrementAndGet(),"MS Dhoni",Team.CSK));
        playerList.add(new Player(id.incrementAndGet(), "Ravindra Jadeja", Team.MI));
        playerList.add(new Player(id.incrementAndGet(), "Suresh Raina ", Team.MI));
        playerList.add(new Player(id.incrementAndGet(), "Rohit Sharma", Team.DC));
        playerList.add(new Player(id.incrementAndGet(), "Jasprit Bumrah", Team.CSK));
    }
}
