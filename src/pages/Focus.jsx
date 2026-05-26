import React, { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import StatCard from '../components/StatCard';

const Focus = () => {
  const [minutes, setMinutes] = useState(25);
  const [seconds, setSeconds] = useState(0);
  const [isRunning, setIsRunning] = useState(false);
  const [totalFocusTime, setTotalFocusTime] = useState(1850);

  useEffect(() => {
    let interval;
    if (isRunning && (minutes > 0 || seconds > 0)) {
      interval = setInterval(() => {
        if (seconds === 0) {
          if (minutes === 0) {
            setIsRunning(false);
          } else {
            setMinutes(minutes - 1);
            setSeconds(59);
          }
        } else {
          setSeconds(seconds - 1);
        }
      }, 1000);
    }
    return () => clearInterval(interval);
  }, [isRunning, minutes, seconds]);

  const toggleTimer = () => setIsRunning(!isRunning);
  const resetTimer = () => {
    setMinutes(25);
    setSeconds(0);
    setIsRunning(false);
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-blue-900 to-black p-6 pt-24">
      <div className="max-w-4xl mx-auto">
        <motion.h1
          className="text-4xl font-bold mb-2 bg-gradient-to-r from-cyan-400 to-blue-400 bg-clip-text text-transparent"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
        >
          🎯 Focus Mode (Pomodoro)
        </motion.h1>
        <p className="text-gray-400 mb-8">Stay focused, one pomodoro at a time</p>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <StatCard title="Today's Focus" value={`${Math.floor(totalFocusTime / 60)}h`} icon="⏱️" />
          <StatCard title="Pomodoros Done" value="12" icon="✅" />
          <StatCard title="Break Time" value="2h 24m" icon="☕" />
        </div>

        <motion.div
          className="bg-gradient-to-br from-slate-800 to-slate-900 border border-cyan-400/30 rounded-xl p-12 text-center"
          initial={{ opacity: 0, scale: 0.9 }}
          animate={{ opacity: 1, scale: 1 }}
        >
          <p className="text-gray-400 mb-6 text-lg">Time Remaining</p>
          <motion.div
            className="text-7xl font-bold text-cyan-400 mb-8 font-mono"
            key={`${minutes}${seconds}`}
          >
            {String(minutes).padStart(2, '0')}:{String(seconds).padStart(2, '0')}
          </motion.div>
          <div className="flex gap-4 justify-center">
            <motion.button
              whileHover={{ scale: 1.05 }}
              whileTap={{ scale: 0.95 }}
              onClick={toggleTimer}
              className="bg-gradient-to-r from-cyan-500 to-blue-500 px-8 py-3 rounded-lg font-semibold text-white hover:shadow-lg hover:shadow-cyan-500/50"
            >
              {isRunning ? '⏸ Pause' : '▶ Start'}
            </motion.button>
            <motion.button
              whileHover={{ scale: 1.05 }}
              whileTap={{ scale: 0.95 }}
              onClick={resetTimer}
              className="bg-gradient-to-r from-red-500 to-orange-500 px-8 py-3 rounded-lg font-semibold text-white hover:shadow-lg hover:shadow-red-500/50"
            >
              🔄 Reset
            </motion.button>
          </div>
        </motion.div>
      </div>
    </div>
  );
};

export default Focus;