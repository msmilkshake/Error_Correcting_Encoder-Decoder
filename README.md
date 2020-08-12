<h2 style="text-align: center;">Stage 1/5: Symbol-level error emulator</h2>

<h2 style="text-align: center;">Description</h2>

<p>Wireless connections are everywhere around us. But due to interference, the information is distorted. In this project, you implement a simple algorithm capable of correcting such errors.</p>

<p>To correct errors, you must first simulate errors. Wireless communication channel will do it for you, but to check the algorithm such emulator will be useful.</p>

<p>In this stage, you should write a program that creates errors in the input text, 1 random error per 3 symbols. An error means that the character is replaced by another random character. For example, “abc” characters can be “*bc” or “a*c” or “ab*”, where * is a random character. You can replace by any character but recommended to use only uppercase and lowercase English letters, spacebar and numbers.</p>

<p>The input contains a single line the text in which you need to make errors. Only one error per 3 symbols!</p>

<h2 style="text-align: center;">Example</h2>

<p>Suppose, the input contains the following line:</p>

<pre><code class="java">Very important text</code></pre>

<p><br>
Then the output should be as the following. Of course, your output won't be the same since this is just simulating random errors.</p>

<pre><code class="java">VeSy QOporlantPt2xt</code></pre>

<p><br>
In this example:<br>
<code class="java">Ver</code> turns into <code class="java">VeS</code>,<br>
<code class="java">y i</code> turns into <code class="java">y Q</code>,<br>
<code class="java">mpo</code> turns into <code class="java">Opo</code>,<br>
<code class="java">rta</code> turns into <code class="java">rla</code>,<br>
<code class="java">nt </code> turns into <code class="java">ntP</code>,<br>
<code class="java">tex</code> turns into <code class="java">t2x</code>,<br>
<code class="java">t</code> turns into <code class="java">t</code>.</p>