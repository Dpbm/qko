import sys

def get_key_string(key:str) -> str:
    key_str = ''
    with open(key, 'r') as file:
        lines = file.readlines()
        total_lines = len(lines)

        for i,line in enumerate(lines):
            replace_char = '' if i == total_lines - 1 else '\\n'
            key_str += line.replace('\n', replace_char)

    return key_str


if __name__ == '__main__':
    if(len(sys.argv) != 2):
        print("Invalid Usage!")
        print("Usage: python3 build_key.py PATH_TO_YOUR_GPG_KEY")
        sys.exit(1)

    key = sys.argv[-1]

    print("Your Parsed Key")
    print(get_key_string(key))
