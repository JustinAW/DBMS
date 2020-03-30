import statistics as stats
import math
import csv

def get_avg (times):
    n = 20
    return sum(times) / n


def get_delta (p, np, stddevp, stddevnp):
    n = 20
    top = (n * stddevp**2) + (n * stddevnp**2)
    bot = (n - 1) + (n - 1)
    sqrt1 = math.sqrt(top / bot)
    sqrt2 = math.sqrt((1 / n) + (1 / n))
    return sqrt1 * sqrt2


def get_t (delta, avgp, avgnp):
    return abs(avgp - avgnp) / delta


if __name__=="__main__":
    print("------------------------------------")
    print("Gathering results for INSERT test...")
    print("------------------------------------")
    p = list()
    np = list()
    with open('primtime.csv', 'r') as csvfile:
        f = csv.reader(csvfile, delimiter='\r')
        f.__next__() #skip header
        for row in f:
            p.append(int(row[0]))
        csvfile.close()
    with open('nonprimtime.csv', 'r') as csvfile:
        f = csv.reader(csvfile, delimiter='\r')
        f.__next__() #skip header
        for row in f:
            np.append(int(row[0]))
        csvfile.close()

    avgp = get_avg(p)
    avgnp = get_avg(np)
    print("Average primary times:\n", avgp)
    print("Average non primary times:\n", avgnp, "\n")

    stddevp = stats.stdev(p)
    stddevnp = stats.stdev(np)
    print("Std dev primary times:\n", stddevp)
    print("Std dev non primary times:\n", stddevnp, "\n")

    delta = get_delta(p, np, stddevp, stddevnp)
    print("Delta_xx primary and non primary times:\n", delta, "\n")

    t = get_t (delta, avgp, avgnp)
    print("T value:\n", t)

    print("\n------------------------------------")
    print("Gathering results for SELECT test...")
    print("------------------------------------")
    sp = list()
    snp = list()
    with open('sprimtime.csv', 'r') as csvfile:
        f = csv.reader(csvfile, delimiter='\r')
        f.__next__() #skip header
        for row in f:
            sp.append(int(row[0]))
        csvfile.close()
    with open('snonprimtime.csv', 'r') as csvfile:
        f = csv.reader(csvfile, delimiter='\r')
        f.__next__() #skip header
        for row in f:
            snp.append(int(row[0]))
        csvfile.close()

    savgp = get_avg(sp)
    savgnp = get_avg(snp)
    print("Average primary times:\n", savgp)
    print("Average non primary times:\n", savgnp, "\n")

    sstddevp = stats.stdev(sp)
    sstddevnp = stats.stdev(snp)
    print("Std dev primary times:\n", sstddevp)
    print("Std dev non primary times:\n", sstddevnp, "\n")

    sdelta = get_delta(sp, snp, sstddevp, sstddevnp)
    print("Delta_xx primary and non primary times:\n", sdelta, "\n")

    st = get_t (sdelta, savgp, savgnp)
    print("T value:\n", st)
