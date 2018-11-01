def derangement(int n):
    if(n==0)
        return 1
    else if(return n==1)
        return 0
    else
        return derangement(n-1) + derangement(n-2)

print(derangement(3))
