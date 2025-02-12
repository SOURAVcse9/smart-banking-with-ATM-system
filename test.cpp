#include <algorithm>
#include <cstdio>
#include <list>
#include <vector>

struct Fraction {
    long a, b;
    Fraction (long aa, long bb = 1) : a(aa), b(bb) {}
    bool operator < (Fraction rhs) const {
        return (__int128) a * rhs.b < (__int128) b * rhs.a;
    }
    bool operator > (Fraction rhs) const { return rhs < *this; }
    bool operator <= (Fraction rhs) const { return !(rhs < *this); }
    bool operator >= (Fraction rhs) const { return !(*this < rhs); }
};

struct Arc {
    size_t first, second;
    std::list<Arc *> ceiling;
    std::list<Arc *>::iterator hm_it;
    long numerator, denominator;
    Fraction support() const {
        return Fraction(numerator, denominator);
    }
    void init() {
        hm_it = std::max_element(ceiling.begin(), ceiling.end(),
                [](const Arc *lhs, const Arc *rhs) {
                    return lhs->support() < rhs->support();
                });
    }
    const Arc *get_hm() const {
        return *hm_it;
    }
    Arc *get_hm() {
        return const_cast<Arc *>(const_cast<const Arc *>(this)->get_hm());
    }
    void merge_hm() {
        auto it = hm_it;
        Arc *hm = *it;
        it = ceiling.erase(it);
        ceiling.splice(it, hm->ceiling);
        hm_it = std::max_element(ceiling.begin(), ceiling.end(),
                [](const Arc *lhs, const Arc *rhs) {
                    return lhs->support() < rhs->support();
                });
    }
};

void get_arcs(const std::vector<long> &a, std::vector<Arc> &arcs) {
    size_t n = a.size() - 1;
    std::vector<long> v;
    std::vector<Arc *> w;
    for (size_t i = 0, j = 0; i <= n && j < n - 3; i++) {
        while (j < n - 3 && v.size() >= 2 && a[i] <= a[v.back()]) {
            arcs[j].first = v[v.size() - 2];
            arcs[j].second = i;
            while (!w.empty() &&
                    arcs[j].first <= w.back()->first &&
                    w.back()->second <= arcs[j].second) {
                arcs[j].ceiling.push_front(w.back());
                w.pop_back();
            }
            w.push_back(&arcs[j]);
            j++;
            v.pop_back();
        }
        v.push_back(i);
    }

    arcs[n - 3].first = 0;
    arcs[n - 3].second = n;
    arcs[n - 3].ceiling.assign(w.begin(), w.end());
}

long solve(std::vector<long> a) {
    size_t n = a.size();
    if (n <= 2)
        return 0;
    if (n == 3)
        return a[0] * a[1] * a[2];
    std::rotate(a.begin(), std::min_element(a.begin(), a.end()), a.end());
    a.push_back(a[0]);

    std::vector<long> accum(n + 1);
    for (size_t i = 1; i <= n; i++)
        accum[i] = accum[i - 1] + a[i] * a[i - 1];

    std::vector<Arc> arcs(n - 2);
    get_arcs(a, arcs);

    long ans = 0;
    for (Arc &arc : arcs) {
        if (arc.first + 2 == arc.second) {
            // leaf nodes
            arc.numerator = a[arc.first] * a[arc.first + 1] * a[arc.second];
            arc.denominator = accum[arc.second] - accum[arc.first] -
                a[arc.first] * a[arc.second];
            ans += arc.numerator;
            continue;
        }

        arc.init();

        arc.denominator = accum[arc.second] - accum[arc.first] -
            a[arc.first] * a[arc.second];
        for (Arc *jp : arc.ceiling) {
            size_t jf = jp->first, js = jp->second;
            arc.denominator -= accum[js] - accum[jf] - a[jf] * a[js];
        }

        // step 1
        while (!arc.ceiling.empty() && arc.get_hm()->support() >=
                std::min(a[arc.first], a[arc.second])) {
            Arc &hm = *arc.get_hm();
            ans -= hm.numerator;
            arc.denominator += hm.denominator;
            arc.merge_hm();
        }

        // calculate support
        size_t c1 = a[arc.first] <= a[arc.second] ? arc.first : arc.second;
        size_t c2 = c1 == 0 ? n : c1;
        arc.numerator = arc.denominator + a[arc.first] * a[arc.second];
        if (arc.first == c1)
            arc.numerator -= a[c1] * a[c1 + 1];
        if (arc.second == c2)
            arc.numerator -= a[c2] * a[c2 - 1];

        if (!arc.ceiling.empty()) {
            Arc *jp = arc.ceiling.front();
            if (jp->first == c1) {
                arc.numerator += a[c1] * a[c1 + 1];
                arc.numerator -= a[jp->first] * a[jp->second];
            }
            Arc *jq = arc.ceiling.back();
            if (jq->second == c2) {
                arc.numerator += a[c2] * a[c2 - 1];
                arc.numerator -= a[jq->first] * a[jq->second];
            }
        }
        arc.numerator *= a[c1];
        ans += arc.numerator;

        // step 2
        while (!arc.ceiling.empty() &&
                arc.support() <= arc.get_hm()->support()) {
            Arc &hm = *arc.get_hm();
            arc.numerator += hm.numerator;
            arc.denominator += hm.denominator;
            arc.merge_hm();
        }
    }

    return ans;
}

template<typename T>
int strict_read_int(T &ref) {
    int c = std::getchar();
    if (c == EOF)
        return EOF;
    T t = c - '0';
    while ((c = std::getchar()) >= '0' && c <= '9')
        t = t * 10 + c - '0';
    ref = t;
    return 1;
}

int main() {
    for (size_t n; strict_read_int(n) == 1; ) {
        n++;

        std::vector<long> a(n);
        for (size_t i = 0; i < n; i++)
            strict_read_int(a[i]);

        std::printf("%ld\n", solve(a));
    }
}