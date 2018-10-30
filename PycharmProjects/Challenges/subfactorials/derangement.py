def derangement(n):
    if n==0:
        return 1
    elif n==1:
        return 0
    else:
        return (n-1) * (derangement(n-1) + derangement(n-2))

print(derangement(6))