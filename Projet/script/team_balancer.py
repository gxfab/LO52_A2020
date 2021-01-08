from participant import Participant
from team import Team
import sys
import csv
from random import randint
from time import time

from team_splitter import TeamSplitter


class TeamBalancer:
    def __init__(self, teams=None):
        if teams is not None:
            self.teams = teams
        else:
            self.teams = []

        self.score = sys.float_info.max

    def add_team(self, team: Team):
        self.teams.append(team)

    def balance(self, max_iter=1000, threshold=-1, max_tabu_size=100):
        if len(self.teams) == 0:
            raise Exception("Please add some teams before calling this function")

        i = 0
        s_best = self.teams.copy()
        best_candidate = s_best
        s_score = self.__compute_score(best_candidate)
        tabu_list = [s_best]

        while i < max_iter and s_score > threshold:
            neighborhood = self.__get_neighbors(best_candidate)
            best_candidate = neighborhood[0]
            best_score = self.__compute_score(best_candidate)

            for c in neighborhood:
                score = self.__compute_score(c)
                if score < best_score and not self._contains(tabu_list, c):
                    best_candidate = c
                    best_score = score

            if best_score < s_score:
                s_score = best_score
                s_best = best_candidate

            tabu_list.append(best_candidate)

            if len(tabu_list) > max_tabu_size:
                tabu_list.pop(0)

            i += 1

        self.score = s_score
        self.teams = s_best

    def _contains(self, tabu_list, teams):
        for t in tabu_list:
            if self.__are_same(t, teams):
                return True

        return False

    @staticmethod
    def __are_same(teams1, teams2):
        for t in teams1:
            # find the team in teams2
            for t2 in teams2:
                if t.participants[0] in t2.participants:
                    if not all(p in t.participants for p in t2.participants):
                        return False
                    break
        return True

    @staticmethod
    def __get_neighbors(teams):
        neighbors = []

        for i, t in enumerate(teams):
            for j, p in enumerate(t.participants):
                neighbors.append([])
                current = neighbors[-1]

                for t2 in teams:
                    current.append(t2.copy())

                selected_team = i

                # Select the team for switching
                while selected_team == i:
                    selected_team = randint(0, len(current) - 1)

                selected_team = current[selected_team]
                # Select participant for the switch
                participant_i = randint(0, len(selected_team.participants) - 1)

                p1 = current[i].participants.pop(j)
                p2 = selected_team.participants.pop(participant_i)

                current[i].add_participant(p2)
                selected_team.add_participant(p1)

        return neighbors

    @staticmethod
    def __compute_score(teams):
        scores = []
        for t in teams:
            scores.append(t.compute_level())

        a = max(scores) - min(scores)
        return a


if __name__ == '__main__':
    NB_TO_GENERATE = 30
    NB_PER_TEAM = 3
    PARAM_OUTPUT_FILE = "team_balancing_params_out.csv"
    STAT_OUTPUT_FILE = "team_balancing_stats_out.csv"

    STAT_MAX_ITER = 200
    STAT_MAX_TABU = 50

    with open(STAT_OUTPUT_FILE, 'w', newline='') as f:
        writer = csv.writer(f, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)
        writer.writerow(('n', 't', 'score'))
        for i in range(0, 150):
            participants = [Participant(i, str(i + 1), randint(0, 100)) for i in range(NB_TO_GENERATE)]
            splitter = TeamSplitter(participants)

            _teams = splitter.split(NB_PER_TEAM)
            balancer = TeamBalancer(_teams)
            balancer.teams = _teams
            last_time = time()
            balancer.balance(max_iter=STAT_MAX_ITER, max_tabu_size=STAT_MAX_TABU)
            dt = time() - last_time
            writer.writerow((i, dt, balancer.score))
            f.flush()
            print((i, dt, balancer.score))

    participants = [Participant(i, str(i + 1), randint(0, 100)) for i in range(NB_TO_GENERATE)]
    splitter = TeamSplitter(participants)

    _teams = splitter.split(NB_PER_TEAM)
    balancer = TeamBalancer(_teams)
    with open(PARAM_OUTPUT_FILE, 'w', newline='') as f:
        writer = csv.writer(f, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)
        writer.writerow(('max_iter', 'tabu_size', 't', 'score'))
        for i in range(0, 150):
            for j in range(0, 150):
                balancer.teams = _teams
                last_time = time()
                balancer.balance(max_iter=i, max_tabu_size=j)
                dt = time() - last_time
                writer.writerow((i, j, dt, balancer.score))
                f.flush()
                print((i, j, dt, balancer.score))

    # Sanity check
    known_ids = set()
    for _t in balancer.teams:
        for p in _t.participants:
            if p.id in known_ids:
                raise Exception("Participant in twice")
            known_ids.add(p.id)

    for _t in balancer.teams:
        print(_t.compute_level(), [(p.name, p.level) for p in _t.participants])

    print('Score', balancer.score)
