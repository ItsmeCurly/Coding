#include <iostream>
#include <fstream>
#include <cmath>

using namespace std;

double mean(int v[], int size);

double stddev(int v[], int size, double m);

char letter(int score, double m, double s);

string code(string id, string lastName, string firstName);

int main() {
    const int CAPACITY = 24;
    string codes[CAPACITY];
    int midterms[CAPACITY];
    int finals[CAPACITY];

    ifstream fin;
    fin.open(R"(C:\Users\Bonnie\Coding\CLionProjects\Assg6_220\grades.txt)");

    if(fin.fail()) {
        printf("Error opening file");
        return 0;
    }
    int counter = 0;
    string id, lastName, firstName;
    while (CAPACITY > counter) {
        string dept, year;
        fin >> id >> lastName >> firstName;
        codes[counter] = code(id, lastName, firstName);
        fin >> dept >> year;
        fin >> midterms[counter] >> finals[counter];
        counter += 1;
    }
    fin.close();
    double mtMean = mean(midterms, CAPACITY);
    double fnMean = mean(finals, CAPACITY);

    double mtStddev = stddev(midterms, CAPACITY, mtMean);
    double fnStddev = stddev(finals, CAPACITY, fnMean);

    for(int i = 0; i < CAPACITY; i++) {
        cout << codes[i] << " Midterm: " << midterms[i] << "("
             << letter(midterms[i], mtMean, mtStddev) << ")" << "Final: "
             << finals[i] << "(" << letter(finals[i], fnMean, fnStddev) << ")" << endl;
    }
    cout << "Midterm: Mean: " << mtMean << " Standard Deviation: " << mtStddev << endl;
    cout << "Final: Mean " << fnMean << " Standard Deviation: " << fnStddev << endl;

    return 0;
}

double mean(int v[], int size) {
    double sum = 0;
    for (int i = 0; i < size; i++) {
        sum += v[i];
    }
    return sum/size;
}

double stddev(int v[], int size, double m) {
    double temp = 0;
    for (int i = 0; i < size; i++) {
        temp += pow(v[i] - m, 2);
    }
    cout << temp << endl;
    temp /= (size - 1);
    return sqrt(temp);
}

char letter(int score, double m, double s) {
    if(score < m - 1.5 * s)
        return 'F';
    else if(score < m - 0.5 * s)
        return 'D';
    else if(score < m + 0.5 * s)
        return 'C';
    else if(score < m + 1.5 * s)
        return 'B';
    else
        return 'A';
}

string code(const string id, const string lastName, const string firstName) {
    return firstName.substr(0, 1) + lastName.substr(0, 1) + id.substr(5, 2);

}