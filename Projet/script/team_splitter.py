from participant import Participant
from team import Team


class TeamSplitter:
    def __init__(self, participants=None):
        if participants is not None:
            self.participants = participants
        else:
            self.participants = []

    def add_participant(self, p: Participant):
        self.participants.append(p)

    def split(self, nb_per_team: int):
        i = 0
        teams = []
        nb_full_team = len(self.participants) // nb_per_team
        if len(self.participants) % nb_per_team != 0:
            nb_full_team -= 1

        while i < len(self.participants) and len(teams) < nb_full_team:
            teams.append(Team(self.participants[i:i + nb_per_team]))
            i += nb_per_team

        remaining_participants = len(self.participants) - len(teams) * nb_per_team
        if remaining_participants != 0:
            mid = remaining_participants // 2
            teams.append(Team(self.participants[i:i + remaining_participants - mid]))
            teams.append(Team(self.participants[-mid:]))

        return teams


if __name__ == '__main__':
    NB_TO_GENERATE = 34
    NB_PER_TEAM = 4
    participants = [Participant(i, str(i + 1), 0) for i in range(NB_TO_GENERATE)]
    splitter = TeamSplitter(participants)

    _teams = splitter.split(NB_PER_TEAM)
    for t in _teams:
        print(len(t.participants), [p.name for p in t.participants])

    print('Total nb of teams', len(_teams))
