import React, { useState } from 'react';
import { motion } from 'framer-motion';

const Timetable = () => {
  const [schedule] = useState([
    { time: '06:00 AM', activity: 'Morning Exercise', duration: '1 hour', icon: '🏃' },
    { time: '07:00 AM', activity: 'Breakfast', duration: '30 min', icon: '🍳' },
    { time: '08:00 AM', activity: 'UPSC Studies', duration: '3 hours', icon: '📚' },
    { time: '12:00 PM', activity: 'Lunch Break', duration: '1 hour', icon: '🍽️' },
    { time: '01:00 PM', activity: 'Coding Practice', duration: '2 hours', icon: '💻' },
    { time: '03:00 PM', activity: 'Focus Mode', duration: '2 hours', icon: '🎯' },
    { time: '05:00 PM', activity: 'Tea Break', duration: '30 min', icon: '☕' },
    { time: '05:30 PM', activity: 'Fitness Training', duration: '1.5 hours', icon: '💪' },
    { time: '07:00 PM', activity: 'Dinner', duration: '1 hour', icon: '🍜' },
    { time: '08:00 PM', activity: 'Revision', duration: '1 hour', icon: '✏️' },
    { time: '09:00 PM', activity: 'Sleep', duration: '8 hours', icon: '😴' },
  ]);

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-blue-900 to-black p-6 pt-24">
      <div className="max-w-4xl mx-auto">
        <motion.h1
          className="text-4xl font-bold mb-8 bg-gradient-to-r from-cyan-400 to-blue-400 bg-clip-text text-transparent"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
        >
          ⏰ Daily Timetable
        </motion.h1>

        <div className="space-y-4">
          {schedule.map((item, index) => (
            <motion.div
              key={index}
              initial={{ opacity: 0, x: -20 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ delay: index * 0.05 }}
              className="bg-gradient-to-r from-slate-800 to-slate-900 border border-cyan-400/30 rounded-lg p-6 flex items-center justify-between hover:shadow-lg hover:shadow-cyan-500/50 transition-all"
            >
              <div className="flex items-center gap-4">
                <span className="text-4xl">{item.icon}</span>
                <div>
                  <p className="text-cyan-400 font-bold">{item.time}</p>
                  <p className="text-white font-semibold">{item.activity}</p>
                </div>
              </div>
              <span className="text-gray-400">{item.duration}</span>
            </motion.div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Timetable;