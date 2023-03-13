package ru.msm.edu.lesson_2.Sequences;

public class SequenceGeneratorImpl implements SequenceGenerator {

    private void validate(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
    }

    //A. 2, 4, 6, 8, 10...
    @Override
    public void a(int n) {
        add(2, 2, n);
    }

    //    B. 1, 3, 5, 7, 9...
    @Override
    public void b(int n) {
        add(2, 1, n);
    }

    private void add(int addendum, int firstEl, int n) {
        validate(n);
        if (n == 0) return;
        int res = firstEl;
        System.out.println(res);
        for (int i = 1; i < n; i++) {
            res += addendum;
            System.out.println(res);
        }

    }

    //    C. 1, 4, 9, 16, 25...
    @Override
    public void c(int n) {
        validate(n);
        if (n == 0) {
            return;
        }
        System.out.println(1);
        int pre = 1;
        for (int i = 1; i < n; i++) {
            pre = pre + 2 + i + (i - 1);
            System.out.println(pre);

        }
//        cg(n, true);  //alternative solution
    }

    //    D. 1, 8, 27, 64, 125...
    @Override
    public void d(int n) {
        validate(n);
        if (n == 0) {
            return;
        }
        for (int i = 1; i <= n; i++) {
            try {
                System.out.println((int) Math.pow(i, 3));
            } catch (ClassCastException ex) {
                System.out.printf("i = %d\n%s", i, ex.getMessage());
            }
        }
    }

    //    E. 1, -1, 1, -1, 1, -1...
    @Override
    public void e(int n) {
        validate(n);
        if (n == 0) {
            return;
        }
        if (n == 1) {
            System.out.println(1);
        } else {
            for (int i = 0; i < n / 2; i++) {
                System.out.println("1\n-1");
            }
            if (n % 2 == 1) {
                System.out.println(1);
            }
        }
//        eh(n, true);  //alternative solution
    }

    //    F. 1, -2, 3, -4, 5, -6...
    @Override
    public void f(int n) {
        validate(n);
        if (n == 0) {
            return;
        }
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 1) {
                System.out.println(i);
            } else {
                System.out.println(-i);
            }
        }

    }

    //    G. 1, -4, 9, -16, 25....
    @Override
    public void g(int n) {
        validate(n);
        if (n == 0) {
            return;
        }
        System.out.println(1);
        int pre = 1;
        for (int i = 1; i < n; i++) {
            pre = pre + 2 + i + (i - 1);
            System.out.println(i % 2 == 1 ? -pre : pre);
        }
//        cg(n, false);  //alternative solution
    }

    private void cg(int n, boolean isC) {
        validate(n);
        if (n == 0) {
            return;
        }
        System.out.println(1);
        int pre = 1;
        for (int i = 1; i < n; i++) {
            pre = pre + 2 + i + (i - 1);
            if (isC) {
                System.out.println(pre);
            } else {
                System.out.println(i % 2 == 1 ? -pre : pre);
            }
        }
    }

    //    H. 1, 0, 2, 0, 3, 0, 4....
    @Override
    public void h(int n) {
        validate(n);
        if (n == 0) {
            return;
        }
        if (n == 1) {
            System.out.println(1);
        } else {
            for (int i = 1; i <= n / 2; i++) {
                System.out.printf("%d\n0\n", i);
            }
            if (n % 2 == 1) {
                System.out.println(n / 2 + 1);
            }
        }
//        eh(n, false);  //alternative solution
    }

    private void eh(int n, boolean isE) {
        validate(n);
        if (n == 0) {
            return;
        }
        if (n == 1) {
            System.out.println(1);
        } else {
            for (int i = 1; i <= n / 2; i++) {
                if (isE) {
                    System.out.println("1\n-1");
                } else {
                    System.out.printf("%d\n0\n", i);
                }
            }
            if (n % 2 == 1) {
                System.out.println(isE ? 1 : n / 2 + 1);
            }
        }
    }

    //    I. 1, 2, 6, 24, 120, 720...
    @Override
    public void i(int n) {
        validate(n);
        if (n == 0) {
            return;
        }
        int cur = 1;
        System.out.println(cur);
        for (int i = 2; i <= n; i++) {
            cur = cur * i;
            System.out.println(cur);
        }
    }

    //    J. 1, 1, 2, 3, 5, 8, 13, 21…
    @Override
    public void j(int n) {
        validate(n);
        if (n == 0) {
            return;
        }
        if (n == 1) {
            System.out.println(1);
        } else {
            int pre1 = 1;
            int pre2 = 1;
            System.out.println(pre1 + "\n" + pre2);
            for (int i = 2; i < n; i++) {
                int cur = pre1 + pre2;
                System.out.println(cur);
                pre1 = pre2;
                pre2 = cur;
            }
        }
    }
}
