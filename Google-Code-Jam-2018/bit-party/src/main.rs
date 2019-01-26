use std::io;

struct Cashier {
    m: usize,
    s: usize,
    p: usize,
    b: usize,
}

struct BitParty {
    r: usize,
    b: usize,
    _c: usize,
    cashiers: Vec<Cashier>,
}

impl BitParty {
    fn _test_set_2(mut self) -> usize {
        let mut bits = 0;
        let mut time = 0;
        let mut robots = 0;

        while bits < self.b {
            // in the case of infinite many robots this line does all the job
            let mut min = self.cashiers
                .iter_mut()
                .filter(|c| c.m > c.b )
                .min_by(|l, r| {
                    let l = ((l.b + 1) * l.s) + l.p;
                    let r = ((r.b + 1) * r.s) + r.p;
                    l.cmp(&r)
                })
                .unwrap();
            min.b += 1;

            bits += 1;
            time = min.b * min.s + min.p;

            // if the number of robots is limited
            if min.b == 1 {
                robots += 1;
                if robots > self.r {
                    let w = self.cashiers
                        .iter()
                        .filter(|c| c.b > 0)
                        .min_by(|l, r| l.m.cmp(&r.m))
                        .unwrap();
                    // we can think of this as a tax on the amount of bits we can process in a
                    // given amount of time due to the limited amount of robots
                    bits -= w.b;
                    // no need to update time, it only grows
                }
            }
        }

        time
    }
}

fn main() -> std::io::Result<()> {
    let mut n = String::new();
    io::stdin().read_line(&mut n)?;

    let n: usize= n.trim().parse().unwrap();

    for case in 0..n {
        let mut a = String::new();
        io::stdin().read_line(&mut a)?;

        let mut words = a.split_whitespace();
        let r = words.next().unwrap().trim().parse().unwrap();
        let b = words.next().unwrap().trim().parse().unwrap();
        let c = words.next().unwrap().trim().parse().unwrap();

        let mut cashiers = Vec::with_capacity(c as usize);

        for _ in 0..c {
            let mut buf = String::new();
            io::stdin().read_line(&mut buf)?;

            let mut words = buf.split_whitespace();

            let m = words.next().unwrap().trim().parse().unwrap();
            let s = words.next().unwrap().trim().parse().unwrap();
            let p = words.next().unwrap().trim().parse().unwrap();

            cashiers.push(Cashier { m, s, p, b: 0 });
        }

        let bp = BitParty { r, b, _c: c, cashiers };

        print!("Case #{}:{}\n", case + 1, bp._test_set_2());
    }

    Ok(())
}
