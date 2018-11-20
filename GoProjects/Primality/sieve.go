package main

var nums []bool

func listPrimes(n int) (a []int) {
	nums = make([]bool, n-2)
	//initializes the array of numbers from {2...n} with true values
	for i := range nums {
		nums[i] = true
	}

	/*
		the sieve algorithm
		will start at i = 2 and start marking all false values starting at i ** 2
		will then find the next true value of the array nums until i ** 2 > n
		the final array of true values will then be returned, as these are the prime values
	*/
	for i := 2; (i * i) < n; {
		for j := i * i; j < n; j += i {
			nums[j-2] = false
		}
		for k, v := range nums {
			if k > (i-2) && v {
				i = k + 2
				break
			}
		}
	}
	//appends correct values to output
	for i, v := range nums {
		if v {
			a = append(a, i+2)
		}
	}
	return
}

func isPrime(n int) (a []int) {
	a = append(a, 2)
	a = append(a, 3)
	for i := 4; i < n; i++ {
		divides := false
		for j := i - 1; j > 3; j-- {
			if i%j == 0 {
				divides = true
				break
			}
		}
		if divides {
			a = append(a, i)
		}
	}
	return
}
