#!/usr/bin/env python3
"""
Наближений розрахунок метрик для Java-класів (Завдання 1).
Використання:  python3 metrics.py <файл.java> [<файл.java> ...]

Конвенції:
  WMC  = сума цикломатичних складностей методів класу (CC = 1 + if/for/while/case/catch/&&/||/?:).
  CBO  = кількість різних типів (окрім java.lang.* та примітивів), на які посилається клас.
  LCOM = LCOM4: кількість компонент зв'язності графа методів (метод ~ метод, якщо мають спільне поле
         або один викликає інший). 1 = клас цілісний; >1 = клас можна розбити.
Для остаточних цифр у звіті перевірте результат інструментом (MetricsReloaded / CK).
"""
import re, sys

JAVA_LANG = {"String","System","Object","Exception","IllegalArgumentException","IllegalStateException",
             "Math","Override","Integer","Double","Boolean","Long","Character","StringBuilder",
             "RuntimeException","Iterable","Comparable","Void","Record","Enum","UnsupportedOperationException"}

def strip(src):
    src = re.sub(r'/\*.*?\*/', '', src, flags=re.S)
    src = re.sub(r'"(\\.|[^"\\\n])*"', '""', src)   # спочатку рядки (бо в них може бути «//»)
    src = re.sub(r'//[^\n]*', '', src)
    return src

def split_classes(src):
    """Повертає [(name, body)] для верхньорівневих class/record/enum/interface."""
    res = []
    for m in re.finditer(r'\b(?:class|record|enum|interface)\s+(\w+)[^{;]*\{', src):
        depth, i = 1, m.end()
        while i < len(src) and depth:
            depth += {'{':1,'}':-1}.get(src[i],0); i += 1
        res.append((m.group(1), src[m.end():i-1]))
    return res

def methods(body):
    out = []
    for m in re.finditer(r'(?:(?:public|private|protected|static|final|default|abstract)\s+)*'
                         r'(?:[\w<>\[\],.?]+\s+)?(\w+)\s*\([^)]*\)\s*(?:throws[\w\s,]+)?\{', body):
        name = m.group(1)
        if name in {"if","for","while","switch","catch","try","synchronized"}: continue
        depth, i = 1, m.end()
        while i < len(body) and depth:
            depth += {'{':1,'}':-1}.get(body[i],0); i += 1
        # лише методи верхнього рівня класу (глибина 0 на початку)
        if body[:m.start()].count('{') - body[:m.start()].count('}') == 0:
            out.append((name, body[m.end():i-1]))
    return out

def cc(code):
    return 1 + len(re.findall(r'\b(?:if|for|while|case|catch)\b|&&|\|\||\?', code))

def analyse(path):
    src = strip(open(path, encoding='utf8').read())
    for name, body in split_classes(src):
        ms = methods(body)
        wmc = sum(cc(b) for _, b in ms)
        types = {t for t in re.findall(r'\b([A-Z][A-Za-z0-9]*)\b', body)
                 if not t.isupper() and t != name and t not in JAVA_LANG}
        fields = set(re.findall(r'private\s+(?:final\s+)?[\w<>\[\],. ]+?\s+(\w+)\s*[;=]', body))
        names = [n for n, _ in ms]
        uses = {n: {f for f in fields if re.search(r'\b'+f+r'\b', b)} for n, b in ms}
        calls = {n: {o for o in names if o != n and re.search(r'\b'+o+r'\s*\(', b)} for n, b in ms}
        parent = {n: n for n in names}
        def find(x):
            while parent[x] != x: x = parent[x]
            return x
        for a in names:
            for b in names:
                if a < b and (uses[a] & uses[b] or b in calls[a] or a in calls[b]):
                    parent[find(a)] = find(b)
        lcom = len({find(n) for n in names}) if names else 0
        print(f"{name:26} methods={len(ms):2}  WMC={wmc:3}  CBO={len(types):2}  LCOM4={lcom}")

for p in sys.argv[1:]:
    analyse(p)
