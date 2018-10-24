#include <iostream>
#include <string>
#include <fstream>

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
    fin.open("grades.txt");

    if(fin.fail()) {
        printf("Error opening file");
        return 0;
    }
    int counter = 0;
    string id, lastName, firstName;
    while(!fin.eof()) {
        string dept, year;
        int mt, fn;
        fin >> id >> lastName >> firstName;
        codes[counter] = code(id, lastName, firstName);
        fin >> dept >> year;
        fin >> mt >> fn;
    }
    fin.close();
    double mtMean = mean(midterms, CAPACITY);
    double fnMean = mean(finals, CAPACITY);

    double mtStddev = stddev(midterms, CAPACITY, mtMean);
    double fnStddev = stddev(finals, CAPACITY, fnMean);

    for(int i = 0; i < CAPACITY; i++) {
        cout << codes[i] << "Midterm: " << midterms[i] << "(" << letter(midterms[i], mtMean, mtStddev) << ")" << "Final: " << finals[i] << "(" << letter(finals[i], fnMean, fnStddev) << ")";
    }
    cout << "Midterm: Mean: " << mtMean << " Standard Deviation: " << mtStddev;

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
    return lastName.substr(0,1) + firstName.substr(0,1) + id.substr(5,2);

}