//paul Mulcahy
//This program computes values for a heat exchanger with a regenerator

#include <iostream>
#include <iomanip>
#include <cmath>

using namespace std;

int main()
{
    //VARIABLES
    const double TA = 500;          //  temp at A in rankine
    const double PA = 14.7;         // pressure at A in psia
    double TB;                      //Temp at TB
    double PB;                      //PRESSURE AT PB
    double TC;                      //TEMP AT c
    double PC;                      //PRESSURE AT C
    const double TD = 1800;         //TEMP AT d in rankine
    double PD;                      //PRESSURE AT d
    double TE;                      //TEMP AT exchanger
    double PE;                      // PRESSURE AT e
    double TF;                      //TEMP AT for
    double PF;                      //PRESSURE AT for
    double QH;                      //HEAT EXCHANGE AT HI EXCHANGE
    double QL;                      //HEAT EXCHANGE AT LOW
    double WT;                      //WORK AT TURBINE
    double WC;                      //WORK AT COMPRESSOR

    double NREGEN;                  //EFFICIENCY OF NREGEN
    double ncomp;                   // efficiency of COMPRESSOR
    double nturb;                   //efficiency of TURBINE
    double PR;                      //PRESSURE RATIO
    double CCP;                      //CHANGE IN P

    double M;                       //MASS FLOW

    double WABP;                    //Work isentropic comp
    double TBP;                     //TEMP AT B PRIME
    double WDEP;                    //WORK IN ISENTROPIC TURBINE
    double TEP;                     //TEMP AT E PRIME
    double WNET;                    // WORK NET
    double NTHERM;                   //THERMAL EFFICIENCY
    double ICYC;                     //IRREVSINILITY CYCLIC

    //INPUT SECTION
    cout << setprecision(2)<<right<<fixed;
    cout << "input pressure ratio-";
    cin >> CCP;
    cout << "input compressor efficiency-";
    cin >> ncomp;
    cout << "input turbine efficiency-";
    cin >> nturb;
    cout << "input mass flow rate-";
    cin >> M;

    //processing section

    TBP = (pow(CCP, .286) * 500);
    WABP = - (M * .24 *(TBP - 500));
    WC = ( WABP / ncomp );
    TEP = (pow(CCP, .286) * 1800);
    WDEP = - (M * .24 * ( TEP - 1800));
    WT = ( WDEP / nturb );
    WNET = (WC + WT);

    QL = (M * 1 * (590 - 495));

    TB = (((-1 * WC) / (M * .24)) + 500);
    TE = (( ( -1 * WT) / ( M * .24 ) ) +1800 );
    TC = ((.49 * ( TE - TB)) + TB );
    QH = (M * .24 * ( 1800 - TC));

    NTHERM = (( QH - QL )/ (QH));

    ICYC = ((QH / 2000) + ( QL / 2000));
}