class Participant:
    def __init__(self, id, name, level):
        self.id = id
        self.name = name
        self.level = level

    def __eq__(self, other):
        if not isinstance(other, Participant):
            return False

        return self.id == other.id
