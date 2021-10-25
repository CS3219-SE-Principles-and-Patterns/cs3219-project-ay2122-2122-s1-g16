import pathlib

import pandas as pd

## download https://github.com/haoel/leetcode/tree/master/algorithms/cpp and save to local
dir = pathlib.Path('./leetcode_cpp')


def main():
    df = pd.DataFrame(columns=['question_id', 'title', 'content', 'difficulty', 'solution'])
    solution_files = []
    for solution_dir in dir.iterdir():
        for solution_file in solution_dir.glob('*.cpp'):
            solution_files.append(solution_file)

    for i in range(len(solution_files)):
        solution_file = solution_files[i]
        with open(solution_file) as f:
            try:
                lines = f.readlines()
                content, solution = parse(lines)
                df.loc[i] = [i + 1, solution_file.name.split(".cpp")[0], content, get_difficulty(lines), solution]
            except Exception:
                continue

    df.to_excel("leetcode_solutions.xlsx")


def get_difficulty(lines):
    if len(lines) <= 100:
        return 0
    elif len(lines) <= 180:
        return 1
    else:
        return 2


def parse(lines):
    content = ""
    solution = ""
    i = 0
    while not lines[i].startswith("/**"):
        i += 1
    i += 1
    while lines[i].strip() == "*":
        i += 1
    while lines[i].strip().startswith("*"):
        stripped = lines[i].strip()[1:].strip()
        if stripped.startswith("*"):
            pass
        elif stripped:
            content += stripped
        else:
            content += "\n"
        i += 1
    while i < len(lines):
        if lines[i].strip():
            solution += lines[i]
        i += 1

    return content, solution


if __name__ == "__main__":
    main()