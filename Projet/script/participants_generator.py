import random
import json


FILE_NAME = 'names.txt'
OUTPUT_FILE_NAME = 'participants.json'
NB_TO_CREATE = 30
MIN_LEVEL = 0
MAX_LEVEL = 100

if __name__ == '__main__':
    with open(FILE_NAME, 'r') as f:
        participants = f.readlines()
        out = []

        for i in range(NB_TO_CREATE):
            out.append({
                'name': participants[random.randint(0, len(participants) - 1)].replace('\n', ''),
                'level': random.randint(0, 100)
            })

        with open(OUTPUT_FILE_NAME, 'w') as of:
            json.dump(out, of)
