package com.tibiacalcs.processor.data.world;

import com.tibiacalcs.core.world.WorldOnlineHistory;
import com.tibiacalcs.core.world.WorldOnlineHistoryRepository;
import com.tibiacalcs.processor.task.Task;
import com.tibiacalcs.processor.task.TaskType;
import com.tibiacalcs.tibiadata.api.world.WorldListResponse;
import com.tibiacalcs.tibiadata.api.world.WorldsApi;
import com.tibiacalcs.tibiadata.api.world.entities.WorldInformation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class WorldUpdater extends Task {

  private final WorldOnlineHistoryRepository worldRepository;
  private final WorldsApi worldsApi;

  @Getter
  private List<WorldInformation> worldList = new ArrayList<>();

  private Date lastUpdated;

  @Scheduled(initialDelay = 0, fixedDelay = 30 * 1000)
  public void updateWorlds() {
    this.process();
  }

  @Override
  protected TaskType getType() {
    return TaskType.WORLD_UPDATER;
  }

  @Override
  protected void processImpl() {
    WorldListResponse response = worldsApi.worlds();

    this.worldList = response.getWorlds().getAllworlds();

    if (this.lastUpdated != null && this.lastUpdated
        .equals(response.getInformation().getLastUpdated())) {
      log.trace("World list is outdated.");

      return;
    }

    this.lastUpdated = response.getInformation().getLastUpdated();

    log.debug("Got a new world list update({}) with {} worlds.", this.lastUpdated,
        this.worldList.size());

    this.worldRepository
        .insert(worldList.stream().map(world -> new WorldOnlineHistory().setName(world.getName())
            .setDate(response.getInformation().getLastUpdated()).setOnline(world.getOnline()))
            .collect(
                Collectors.toList()));
  }
}
