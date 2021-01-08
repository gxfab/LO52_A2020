from participant import Participant


class Team:
    def __init__(self, participants=None):
        if participants is not None:
            self.participants = participants
        else:
            self.participants = []

    def add_participant(self, p: Participant):
        self.participants.append(p)

    def compute_level(self):
        return sum([p.level for p in self.participants])

    def copy(self):
        return Team(self.participants.copy())
