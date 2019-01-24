use std::io;

#[derive(Debug)]
struct Waffle {
    r: usize,
    c: usize,
    h: usize,
    v: usize,
    data: Vec<Vec<usize>>,
    diners_count: usize,
}

impl Waffle {
    fn new(r: usize, c: usize, h: usize, v: usize, data: Vec<Vec<usize>>,) -> Self {
        Waffle {
            r,
            c,
            h,
            v,
            data,
            diners_count: (h + 1) * (v + 1),
        }
    }

    fn chips_in(&self, t: usize, b: usize, l: usize, r: usize) -> usize {
        let mut chips = 0;
        for i in t..b {
            for j in l..r {
                chips += self.data[i][j];
            }
        }

        chips
    }

    // Try every possible cut.
    fn _test_set_1(&self) -> String {
        let chips: usize = self.data.iter().map::<usize, _>(|row| row.iter().sum() ).sum::<usize>();
        if  chips % (self.h * self.v) != 0 {
            return String::from("IMPOSSIBLE");
        }

        for i in 0..self.r {
            for j in 0..self.c {
                let a = self.chips_in(0, i, 0, j);
                let b = self.chips_in(0, i, j, self.c);
                let c = self.chips_in(i, self.r, 0, j);
                let d = self.chips_in(i, self.r, j, self.c);

                if a == b && b == c && c == d {
                    return String::from("POSSIBLE");
                }
            }
        }
        String::from("IMPOSSIBLE")
    }

    // Observe that only one single cut is possible (excluding empty rows/lines)
    fn _test_set_2(&self) -> String {
        let chips: usize = self.data.iter().map::<usize, _>(|row| row.iter().sum() ).sum::<usize>();
        if  chips % self.diners_count != 0 {
            return String::from("IMPOSSIBLE");
        }

        if chips == 0 {
            return String::from("POSSIBLE");
        }

        let row_sums: Vec<usize> = self.data.iter().map(|row| -> usize { row.iter().sum() } ).collect();
        let mut col_sums: Vec<usize> = vec![0; self.c];
        for j in 0..self.c {
            for i in 0..self.r {
                col_sums[j] += self.data[i][j];
            }
        }

        let portion = chips / self.diners_count;

        let mut row_indices: Vec<usize> = vec![];
        let mut col_indices: Vec<usize> = vec![];

        row_indices.push(0);
        let mut s = 0;
        for (i, val) in row_sums.iter().enumerate() {
            s += val;
            if s == (chips / (self.h + 1)) {
                row_indices.push(i + 1);
                s = 0;
            }

            if s > (chips / (self.h + 1)) {
                return String::from("IMPOSSIBLE");
            }
        }

        col_indices.push(0);
        let mut s = 0;
        for (i, val) in col_sums.iter().enumerate() {
            s += val;
            if s == (chips / (self.v + 1)) {
                col_indices.push(i + 1);
                s = 0;
            }

            if s > (chips / (self.v + 1)) {
                return String::from("IMPOSSIBLE");
            }
        }

        for i in 0..self.h + 1 {
            for j in 0..self.v + 1 {
                let t = row_indices[i];
                let b = row_indices[i + 1];
                let l = col_indices[j];
                let r = col_indices[j + 1];

                if self.chips_in(t, b, l, r) != portion {
                    return String::from("IMPOSSIBLE");
                }
            }
        }

        String::from("POSSIBLE")
    }
}

fn main() -> std::io::Result<()> {
    let mut n = String::new();
    io::stdin().read_line(&mut n)
        .expect("Failed to read line");

    let n: usize= n.trim().parse().unwrap();

    for case in 0..n {
        let mut a = String::new();
        io::stdin().read_line(&mut a)
            .expect("Failed to read line");

        let mut words = a.split_whitespace();
        let r = words.next().unwrap().trim().parse().unwrap();
        let c = words.next().unwrap().trim().parse().unwrap();
        let h = words.next().unwrap().trim().parse().unwrap();
        let v = words.next().unwrap().trim().parse().unwrap();
        let mut data = vec![Vec::new(); r];

        for i in 0..r {
            let mut buf = String::new();
            io::stdin().read_line(&mut buf)
                .expect("Failed to read line");

            data[i] = buf.trim().chars().map(
                |char| {
                    match char {
                        '@' => 1,
                        _ => 0,
                    }
                }).collect();
        }

        let waffle = Waffle::new(r, c, h, v, data);

        print!("Case #{}:{:?}\n", case + 1, waffle._test_set_2());
    }

    Ok(())
}
